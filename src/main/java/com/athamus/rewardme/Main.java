package com.athamus.rewardme;

import com.athamus.rewardme.events.BlockBreak;
import com.athamus.rewardme.events.MobKill;
import com.athamus.rewardme.managers.CooldownHandler;
import com.athamus.rewardme.managers.FileManager;
import com.athamus.rewardme.managers.RewardManager;
import com.athamus.rewardme.util.Counter;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class Main extends JavaPlugin {

    //Config Initialization
    private File config;
    private FileConfiguration c;

    //Reward Initialization
    private FileConfiguration rewardFile;
    private RewardManager rewardManager;
    private FileManager fileManager;

    //Cooldown Initialization
    private CooldownHandler cooldownHandler;

    //Counter Initialization
    private Counter counter;

    //Vault
    private static final Logger log = Logger.getLogger("Minecraft");
    private static Economy econ = null;
    private static Permission perms = null;
    private static Chat chat = null;

    @Override
    public void onEnable() {
        // Plugin startup logic
        loadConfig();
        registerCommands();
        registerEvents();
        rewardFile = new FileManager(this).getFile();
        cooldownHandler = new CooldownHandler(this);
        counter = new Counter(this);
        rewardManager = new RewardManager(this);
        fileManager = new FileManager(this);

        //Vault
        if (!setupEconomy() ) {
            log.severe(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        setupPermissions();
        setupChat();
        fileManager.setupFile();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
        fileManager.saveFile();
    }

    public String TCC(String input){return ChatColor.translateAlternateColorCodes('&', input);}

    public void registerEvents(){
        new BlockBreak(this);
        new MobKill(this);
    }

    //Config
    public FileConfiguration config() {
        return this.c;
    }

    public void loadConfig(){
        this.config = new File(this.getDataFolder(), "config.yml");
        if (!this.config.exists()) {
            this.config.getParentFile().mkdirs();
            this.saveResource("config.yml", false);
        }

        this.c = new YamlConfiguration();

        try {
            this.c.load(this.config);
        } catch (InvalidConfigurationException | IOException var2) {
            var2.printStackTrace();
        }
    }

    public void saveFile(FileConfiguration fileconfig, File file) {
        try {
            fileconfig.save(file);
        } catch (IOException var4) {
            var4.printStackTrace();
        }

    }

    public void registerCommands(){}

    public FileConfiguration getRewardFile(){return  rewardFile;}
    public CooldownHandler getCooldownHandler(){return cooldownHandler;}
    public Counter getCounter(){return counter;}
    public FileManager getFileManager(){return fileManager;}
    public RewardManager getRewardManager(){return rewardManager;}

    //Vault

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    private boolean setupPermissions() {
        RegisteredServiceProvider<Permission> rsp = getServer().getServicesManager().getRegistration(Permission.class);
        perms = rsp.getProvider();
        return perms != null;
    }


    public static Economy getEconomy() {
        return econ;
    }

    public static Permission getPermissions() {
        return perms;
    }

    public static Chat getChat() {
        return chat;
    }
}
