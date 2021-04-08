package com.chaosqk.sw.items;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SpawnTeleporterItem extends ItemStack {

    public SpawnTeleporterItem()
    {
        super(Material.MAGMA_CREAM, 1);
        ItemMeta itemMeta = this.getItemMeta();
        itemMeta.setDisplayName("§3§lHome Teleporter");
        List<String> lore = new ArrayList<>();
        lore.add("§eRight click this item and it will take you home.");
        itemMeta.addEnchant(Enchantment.LURE, 1, true);
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setLore(lore);
        this.setItemMeta(itemMeta);
    }
}
