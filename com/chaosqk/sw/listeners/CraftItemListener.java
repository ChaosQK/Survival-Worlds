package com.chaosqk.sw.listeners;

import com.chaosqk.sw.SWorlds;
import com.chaosqk.sw.crafts.WorldCoreRecipe;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

public class CraftItemListener implements Listener {

    private SWorlds plugin;

    public CraftItemListener(SWorlds plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onItemCraft(CraftItemEvent e)
    {
        ItemStack item = e.getRecipe().getResult();
        if(item.getItemMeta().getDisplayName().equals("§6§lWorld Core"))
        {
            String worldName = "world" + this.plugin.getNextWorldNumber();
            this.plugin.addWorld(worldName);
            new WorldCoreRecipe(this.plugin);
            this.plugin.createWorld(worldName);
            Bukkit.broadcastMessage("§4§l§k|||§r §3A §6§lWorld Core §3has been crafted ! §r§4§l§k|||");
            for(Player p : Bukkit.getOnlinePlayers())
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, 60f, 0f);
        }
    }

}
