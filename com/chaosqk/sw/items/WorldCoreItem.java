package com.chaosqk.sw.items;

import com.chaosqk.sw.SWorlds;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class WorldCoreItem extends ItemStack {

    public WorldCoreItem(SWorlds plugin, String worldName)
    {
        super(Material.PLAYER_HEAD, 1);
        SkullMeta skullMeta = (SkullMeta) this.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), null);
        profile.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM4Y2YzZjhlNTRhZmMzYjNmOTFkMjBhNDlmMzI0ZGNhMTQ4NjAwN2ZlNTQ1Mzk5MDU1NTI0YzE3OTQxZjRkYyJ9fX0=", ""));
        try {
            Field profileField = skullMeta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(skullMeta, profile);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        skullMeta.setDisplayName("§6§lWorld Core");
        List<String> lore = new ArrayList<>();
        lore.add("§dCore's Destination : §f" + worldName);
        lore.add("§eThis item has the power to activate a World Portal");
        skullMeta.setLore(lore);
        this.setItemMeta(skullMeta);
    }

}
