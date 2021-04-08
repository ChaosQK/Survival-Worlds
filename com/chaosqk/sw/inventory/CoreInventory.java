package com.chaosqk.sw.inventory;

import com.chaosqk.sw.SWorlds;
import com.chaosqk.sw.items.WorldCoreItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CoreInventory implements Listener {

    private SWorlds plugin;

    private Inventory inventory;

    private String portalName;

    public CoreInventory(SWorlds plugin, String portalName)
    {
        this.plugin = plugin;
        this.portalName = portalName;
        inventory = Bukkit.createInventory(null, 9*3, "§3§lWorld Generator");

        initInventory();

        if(this.plugin.isPortalActive(portalName))
        {
            String worldName = this.plugin.getPortalWorldName(portalName);
            ItemStack worldCore = new WorldCoreItem(this.plugin, worldName);
            inventory.setItem(13, worldCore);
        }
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    public void initInventory()
    {
        List<Integer> pass = Arrays.asList(3, 4, 5, 12, 13, 14, 21, 22, 23);
        for(int i = 0; i < inventory.getSize(); ++i)
        {
            if(!pass.contains(i))
                inventory.setItem(i, new ItemStack(Material.BLACK_STAINED_GLASS_PANE, 1));
        }

        for(int pos : pass)
        {
            boolean isActive = (boolean)this.plugin.getPortalMap().get(portalName).get("isActive");
            if(pos != 13)
                inventory.setItem(pos, new ItemStack(isActive ? Material.LIME_CONCRETE : Material.RED_CONCRETE, 1));
        }
    }

    public void open(Player p)
    {
        p.openInventory(inventory);
    }

    @EventHandler
    public void onInventoryInteract(InventoryClickEvent e)
    {
        if(e.isShiftClick() && e.getInventory().equals(this.inventory))
            e.setCancelled(true);

        Inventory inv = e.getClickedInventory();
        if(inv != null && inv.equals(this.inventory))
        {
            if(e.getSlot() == 13)
            {
                if(!e.getCursor().getType().equals(Material.AIR))
                {
                    if(e.getCursor().getItemMeta().getDisplayName().equals("§6§lWorld Core"))
                            setWorld(e);
                    else
                        e.setCancelled(true);
                }
                else if(e.getCurrentItem() != null && !e.getCurrentItem().getType().equals(Material.AIR))
                {
                    if(e.getCurrentItem().getItemMeta().getDisplayName().equals("§6§lWorld Core"))
                            setWorld(e);
                    else
                        e.setCancelled(true);
                }
            }
            else
                e.setCancelled(true);
        }
    }

    public void setWorld(InventoryClickEvent e)
    {
        Player p = ((Player)e.getWhoClicked());
        this.plugin.setPortalStatus(this.portalName, this.inventory.getItem(13) == null);
        if(this.inventory.getItem(13) == null)
            this.plugin.setWorldToPortal(portalName, e.getCursor().getItemMeta().getLore().get(0).split("§f")[1]);
        else
            this.plugin.removeWorldFromPortal(portalName);
        initInventory();
        Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
            p.updateInventory();
        }, 2);
    }


}
