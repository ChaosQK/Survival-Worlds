package com.chaosqk.sw.listeners;

import com.chaosqk.sw.SWorlds;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    private SWorlds plugin;

    public PlayerMoveListener(SWorlds plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e)
    {
        Player p = e.getPlayer();
        if(p.getWorld() == Bukkit.getWorlds().get(0))
        {
            Block b = p.getWorld().getBlockAt(p.getLocation().getBlockX(), p.getLocation().getBlockY()-1, p.getLocation().getBlockZ());
            if((b.getType() == Material.CRYING_OBSIDIAN) || (b.getType() == Material.GILDED_BLACKSTONE))
            {
                String portalName = this.plugin.getPortalNameFromBlock(b);
                if(portalName != null)
                {
                    boolean isActive = this.plugin.isPortalActive(portalName);
                    if(isActive)
                    {
                        String worldName = this.plugin.getPortalWorldName(portalName);
                        World world = Bukkit.getWorld(worldName);
                        int topY = Bukkit.getWorld(worldName).getHighestBlockYAt(p.getLocation().getBlockX(), p.getLocation().getBlockZ());
                        p.teleport(new Location(world, p.getLocation().getBlockX(), topY+1, p.getLocation().getBlockZ()));
                    }
                }
            }
        }
    }

}
