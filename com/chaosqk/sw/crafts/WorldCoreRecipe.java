package com.chaosqk.sw.crafts;

import com.chaosqk.sw.SWorlds;
import com.chaosqk.sw.items.WorldCoreItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

public class WorldCoreRecipe {

    public WorldCoreRecipe(SWorlds plugin)
    {
        ItemStack wc = new WorldCoreItem(plugin, "world" + plugin.getNextWorldNumber());
        NamespacedKey key = new NamespacedKey(plugin, "world_core");
        if(Bukkit.getRecipe(key) != null)
            Bukkit.removeRecipe(key);
        ShapedRecipe worldCore = new ShapedRecipe(key, wc);
        worldCore.shape("GSG", "DTD", "WXL");
        worldCore.setIngredient('G', Material.GRASS_BLOCK);
        worldCore.setIngredient('S', Material.STONE);
        worldCore.setIngredient('D', Material.DIAMOND_BLOCK);
        worldCore.setIngredient('T', Material.NETHER_STAR);
        worldCore.setIngredient('W', Material.WATER_BUCKET);
        worldCore.setIngredient('X', Material.NETHERITE_BLOCK);
        worldCore.setIngredient('L', Material.LAVA_BUCKET);
        Bukkit.addRecipe(worldCore);
    }
}
