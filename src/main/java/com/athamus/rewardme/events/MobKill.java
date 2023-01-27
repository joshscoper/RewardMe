package com.athamus.rewardme.events;

import com.athamus.rewardme.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;

public class MobKill implements Listener {

    private final Main main;

    public MobKill(Main main){
        this.main = main;
        Bukkit.getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onKill(EntityDeathEvent event){
        //Mob Kill Logic
        main.getRewardManager().rewardMob(event.getEntityType(),event.getEntity().getKiller());
    }

}
