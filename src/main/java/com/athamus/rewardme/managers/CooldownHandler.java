package com.athamus.rewardme.managers;

import com.athamus.rewardme.Main;
import org.bukkit.Bukkit;

import java.util.HashMap;

public class CooldownHandler {

    private final Main main;

    public CooldownHandler(Main main){
        this.main = main;
    }

    private final HashMap<String, Integer> cooldownMap = new HashMap<>(); //Should store as "<Player>_<Block/Mob>,<Cooldown>"

    public void setCooldown(String tag, Integer cooldown){
        cooldownMap.put(tag, cooldown);
        startCooldown(tag, cooldown);
    }

    public void removeCooldown(String tag){
        cooldownMap.remove(tag);
    }

    public void startCooldown(String tag,Integer cooldown){
        Bukkit.getScheduler().scheduleSyncDelayedTask(main, () -> removeCooldown(tag),cooldown);
    }

    public Boolean isOnCooldown(String tag){
        return cooldownMap.containsKey(tag);
    }

}
