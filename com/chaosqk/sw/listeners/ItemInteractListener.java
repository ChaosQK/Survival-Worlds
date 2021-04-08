package com.chaosqk.sw.listeners;

import com.chaosqk.sw.SWorlds;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

public class ItemInteractListener implements Listener {

    private SWorlds plugin;

    public ItemInteractListener(SWorlds plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))
        {
            Player p = e.getPlayer();
            ItemStack itemInHand = p.getInventory().getItemInMainHand();
            if(itemInHand != null && itemInHand.getType() != Material.AIR)
            {
                if(itemInHand.getItemMeta().getDisplayName().equals("§3§lHome Teleporter"))
                {
                    p.teleport(p.getBedSpawnLocation() != null ? p.getBedSpawnLocation() : Bukkit.getWorlds().get(0).getSpawnLocation());
                    e.setCancelled(true);
                }
            }
        }
    }

}
