package com.athamus.rewardme.managers;

import com.athamus.rewardme.Main;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileManager {

    private final Main main;
    private final File directory;
    private final File file;
    private FileConfiguration config;

    public FileManager(Main main){
        this.main = main;
        this.directory = new File(main.getDataFolder().getPath());
        this.file = new File(directory, "rewards" + ".yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public void setupFile(){
        if (!directory.exists()){
            directory.mkdirs();
        }
        if (!file.exists()) {
            loadFile();
        }

    }

    // Load File

    public void loadFile(){
        try {
            InputStreamReader reader = new InputStreamReader(main.getResource("rewards.yml"));
            FileConfiguration fileconfig = YamlConfiguration.loadConfiguration(reader);
            config.setDefaults(fileconfig);
            config.options().copyDefaults(true);
            reader.close();
            saveFile();
        } catch(IOException e){
            e.printStackTrace();
        }
    }

    public Boolean fileExists(){
        return file.exists();
    }

    //Save File

    public void saveFile(){
        try{
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public FileConfiguration getFile(){
        return config;
    }

}

