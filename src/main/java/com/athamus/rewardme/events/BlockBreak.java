package com.athamus.rewardme.events;

import com.athamus.rewardme.Main;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreak implements Listener {

    private final Main main;

    public BlockBreak(Main main){
        this.main = main;
        Bukkit.getServer().getPluginManager().registerEvents(this, main);
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        //Block Break Logic
        main.getRewardManager().rewardBlock(event.getBlock().getType(),event.getPlayer());
    }
}
