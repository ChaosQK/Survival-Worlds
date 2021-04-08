package com.chaosqk.sw.listeners;

import com.chaosqk.sw.SWorlds;
import com.chaosqk.sw.utils.PortalCreation;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockIgniteEvent;

public class BlockIgniteListener implements Listener {

    private SWorlds plugin;
    public BlockIgniteListener(SWorlds sWorlds)
    {
        this.plugin = sWorlds;
    }

    @EventHandler
    public void onBlockIgnite(BlockIgniteEvent e)
    {
        Block block = e.getBlock();
        Block igniteBlock = block.getWorld().getBlockAt(block.getX(), block.getY()-1, block.getZ());
        boolean isCore = this.plugin.isBlockPortalCore(igniteBlock);
        if(isPotentialPortal(igniteBlock) && !isCore)
        {
            e.setCancelled(true);
            new PortalCreation(igniteBlock, plugin);
        }
        else if(isCore)
            e.setCancelled(true);
    }

    private boolean isPotentialPortal(Block block)
    {
        return block.getType() == Material.NETHERITE_BLOCK;
    }

}
