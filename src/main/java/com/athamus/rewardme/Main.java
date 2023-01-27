package com.athamus.rewardme;

import com.athamus.rewardme.events.BlockBreak;
import com.athamus.rewardme.events.MobKill;
import com.athamus.rewardme.managers.CooldownHandler;
import com.athamus.rewardme.managers.FileManager;
import com.athamus.rewardme.managers.RewardManager;
import com.athamus.rewardme.util.Counter;
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

        fileManager.setupFile();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
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


}
