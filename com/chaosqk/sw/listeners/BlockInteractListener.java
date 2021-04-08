package com.chaosqk.sw.listeners;

import com.chaosqk.sw.SWorlds;
import com.chaosqk.sw.inventory.CoreInventory;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class BlockInteractListener implements Listener {

    private SWorlds plugin;

    public BlockInteractListener(SWorlds plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e)
    {
        Block block = e.getClickedBlock();
        //int x = block.getX(), y = block.getY(), z = block.getZ();
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK))
        {
            boolean isCore = this.plugin.isBlockPortalCore(block);
            if(isCore)
            {
                String portalName = this.plugin.getPortalNameFromCore(block);
                Player p = e.getPlayer();
                CoreInventory inv = new CoreInventory(this.plugin, portalName);
                inv.open(p);

                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e)
    {
        Block block = e.getBlock();
        boolean isCore = this.plugin.isBlockPortalCore(block);
        if(isCore)
            e.setCancelled(true);
        else if(block.getType().equals(Material.CRYING_OBSIDIAN))
        {
            String portalName = this.plugin.getPortalNameFromBlock(block);
            if(portalName != null)
            {
                String coreCoord = (String)this.plugin.getPortalMap().get(portalName).get("coreBlock");
                String[] coords = coreCoord.split(", ");
                block.getWorld().getBlockAt(Integer.parseInt(coords[0]), Integer.parseInt(coords[1]), Integer.parseInt(coords[2])).setType(Material.NETHERITE_BLOCK);
                this.plugin.removePortal(portalName);
            }
        }
    }

}
