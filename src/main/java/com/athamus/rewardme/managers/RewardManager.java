package com.athamus.rewardme.managers;

import com.athamus.rewardme.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class RewardManager {

    private final Main main;

    public RewardManager(Main main){
        this.main = main;
    }


    //Block Rewards
    public void rewardBlock(Material blockType, Player player){
        //Get Configured Block
       String reward = main.config().getString("BlockRewards.blocks." + blockType.toString().toUpperCase() + ".reward");
       int amount = main.config().getInt("BlockRewards.blocks." + blockType.toString().toUpperCase() + ".amount");
       int cooldown = main.config().getInt("BlockRewards.blocks." + blockType.toString().toUpperCase() + ".cooldown");

       //Logic
        if (reward != null){
            String tag = player.getName() + "_" + blockType.toString().toUpperCase();
            if (!main.getCooldownHandler().isOnCooldown(tag)) {
                if (main.getCounter().getCount(tag) >= amount) {
                    main.getCounter().resetCounter(tag);
                    main.getCooldownHandler().setCooldown(tag, cooldown);
                    //Gives Reward
                    String rewardType = main.getRewardFile().getString(reward + ".type");
                    String rewardTitle = main.getRewardFile().getString(reward + ".reward_title");
                    String rewardMessage = main.getRewardFile().getString(reward + ".reward_message");
                    rewardMessage = rewardMessage.replaceAll("%prefix%", main.getConfig().getString("Configuration.prefix"));
                    String rewardSound = main.getRewardFile().getString(reward + ".reward_sound");
                    switch (rewardType) {
                        case "ITEM":
                            ItemStack item = new ItemStack(Material.valueOf(main.getRewardFile().getString(reward + ".item")));
                            item.setAmount(main.getRewardFile().getInt(reward + ".amount"));
                            if (main.getRewardFile().contains(reward + ".item_meta")) {
                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setDisplayName(main.getRewardFile().getString(reward + ".item_meta.display_name"));
                                itemMeta.setLore(main.getRewardFile().getStringList(reward + ".item_meta.lore"));
                                itemMeta.setCustomModelData(main.getRewardFile().getInt(reward + ".item_meta.custom_model_data"));
                                item.setItemMeta(itemMeta);
                            }
                            if (player.getInventory().firstEmpty() > -1) {
                                player.getInventory().addItem(item);
                            } else {
                                player.getWorld().dropItem(player.getLocation(), item);
                            }
                            break;
                        case "MONEY":
                            main.getEconomy().depositPlayer(player, main.getRewardFile().getDouble(reward + ".amount"));
                            break;
                        case "COMMAND":
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), main.getRewardFile().getString(reward + ".command"));
                            break;
                    }
                    if (rewardMessage != null) {
                        player.sendMessage(rewardMessage);
                    }
                    if (rewardTitle != null) {
                        player.sendTitle(rewardTitle, "", 0, 2, 0);
                    }
                    if (rewardSound != null) {
                        player.playSound(player.getLocation(), Sound.valueOf(rewardSound.toUpperCase()), 1, 1);
                    }
                } else {
                    main.getCounter().setCounter(tag,main.getCounter().getCount(tag)+1);
                }
            }
        }

    }

    //Mob Rewards
    public void rewardMob(EntityType mobType, Player player){
        //Get Configured Mob
        String reward = main.config().getString("BlockRewards.blocks." + mobType.toString().toUpperCase() + ".reward");
        int amount = main.config().getInt("BlockRewards.blocks." + mobType.toString().toUpperCase() + ".amount");
        int cooldown = main.config().getInt("BlockRewards.blocks." + mobType.toString().toUpperCase() + ".cooldown");

        //Logic
        if (!reward.equals(null)){
            String tag = player.getName() + "_" + mobType.toString().toUpperCase();
            if (!main.getCooldownHandler().isOnCooldown(tag)) {
                if (main.getCounter().getCount(tag) >= amount) {
                    main.getCounter().resetCounter(tag);
                    main.getCooldownHandler().setCooldown(tag, cooldown);
                    //Gives Reward
                    String rewardType = main.getRewardFile().getString(reward + ".type");
                    String rewardTitle = main.getRewardFile().getString(reward + ".reward_title");
                    String rewardMessage = main.getRewardFile().getString(reward + ".reward_message");
                    rewardMessage = rewardMessage.replaceAll("%prefix%", main.getConfig().getString("Configuration.prefix"));
                    String rewardSound = main.getRewardFile().getString(reward + ".reward_sound");
                    switch (rewardType) {
                        case "ITEM":
                            ItemStack item = new ItemStack(Material.valueOf(main.getRewardFile().getString(reward + ".item")));
                            item.setAmount(main.getRewardFile().getInt(reward + ".amount"));
                            if (main.getRewardFile().contains(reward + ".item_meta")) {
                                ItemMeta itemMeta = item.getItemMeta();
                                itemMeta.setDisplayName(main.getRewardFile().getString(reward + ".item_meta.display_name"));
                                itemMeta.setLore(main.getRewardFile().getStringList(reward + ".item_meta.lore"));
                                itemMeta.setCustomModelData(main.getRewardFile().getInt(reward + ".item_meta.custom_model_data"));
                                item.setItemMeta(itemMeta);
                            }
                            if (player.getInventory().firstEmpty() > -1) {
                                player.getInventory().addItem(item);
                            } else {
                                player.getWorld().dropItem(player.getLocation(), item);
                            }
                            break;
                        case "MONEY":
                            main.getEconomy().depositPlayer(player, main.getRewardFile().getDouble(reward + ".amount"));
                            break;
                        case "COMMAND":
                            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), main.getRewardFile().getString(reward + ".command"));
                            break;
                    }
                    if (!rewardMessage.equals(null)) {
                        player.sendMessage(rewardMessage);
                    }
                    if (!rewardTitle.equals(null)) {
                        player.sendTitle(rewardTitle, "", 0, 2, 0);
                    }
                    if (!rewardSound.equals(null)) {
                        player.playSound(player.getLocation(), Sound.valueOf(rewardSound.toUpperCase()), 1, 1);
                    }
                }
            }
        }

    }

}
