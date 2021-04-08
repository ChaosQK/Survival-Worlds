package com.chaosqk.sw.crafts;

import com.chaosqk.sw.SWorlds;
import com.chaosqk.sw.items.SpawnTeleporterItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class SpawnTeleporterRecipe {

    public SpawnTeleporterRecipe(SWorlds plugin)
    {
        ItemStack wc = new SpawnTeleporterItem();
        NamespacedKey key = new NamespacedKey(plugin, "spawn_teleporter");
        if(Bukkit.getRecipe(key) != null)
            Bukkit.removeRecipe(key);
        ShapedRecipe spawnTP = new ShapedRecipe(key, wc);
        spawnTP.shape("EEE", "EXE", "EEE");
        spawnTP.setIngredient('E', Material.ENDER_PEARL);
        spawnTP.setIngredient('X', Material.DRAGON_HEAD);
        Bukkit.addRecipe(spawnTP);
    }
}
