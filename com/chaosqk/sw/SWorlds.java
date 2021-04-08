package com.chaosqk.sw;

import com.chaosqk.sw.crafts.SpawnTeleporterRecipe;
import com.chaosqk.sw.crafts.WorldCoreRecipe;
import com.chaosqk.sw.listeners.*;
import com.chaosqk.sw.utils.ParticuleLoop;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.*;

public class SWorlds extends JavaPlugin {

    private Map<String, Map> config = new HashMap<>();
    private Map<String, String> coreBlocks = new HashMap<>();
    private List<Integer> portalNumbers = new ArrayList<>();
    private List<String> worlds = new ArrayList<>();

    public void onEnable()
    {
        saveDefaultConfig();
        Bukkit.getPluginManager().registerEvents(new BlockIgniteListener(this), this);
        Bukkit.getPluginManager().registerEvents(new BlockInteractListener(this), this);
        Bukkit.getPluginManager().registerEvents(new CraftItemListener(this), this);
        Bukkit.getPluginManager().registerEvents(new PlayerMoveListener(this), this);
        Bukkit.getPluginManager().registerEvents(new ItemInteractListener(this), this);
        initConfig();
        new WorldCoreRecipe(this);
        new SpawnTeleporterRecipe(this);
        new ParticuleLoop(this).start();

        for(String world : this.worlds)
        {
            System.out.println(world);
            WorldCreator wc = new WorldCreator(world);
            Bukkit.createWorld(wc);
        }
    }

    public void onDisable() {}

    public void initConfig()
    {
        try {
            Set<String> portals = this.getConfig().getConfigurationSection("portals").getKeys(false);
            for(String keyPortal : portals)
            {
                Map map = new HashMap<>();
                map.put("coreBlock", this.getConfig().getString("portals." + keyPortal + ".coreBlock"));
                map.put("blockCoords", this.getConfig().getStringList("portals." + keyPortal + ".blockCoords"));
                map.put("portalPos", this.getConfig().getStringList("portals." + keyPortal + ".portalPos"));
                map.put("isActive", this.getConfig().getBoolean("portals." + keyPortal + ".isActive"));
                if(this.getConfig().get("portals." + keyPortal + ".targetWorld") != null)
                    map.put("targetWorld", this.getConfig().getString("portals." + keyPortal + ".targetWorld"));
                this.config.put(keyPortal, map);
            }

            this.portalNumbers.addAll(this.getConfig().getIntegerList("portalNumbers"));
            this.worlds.addAll(this.getConfig().getStringList("worlds"));

            Set<String> cores = this.getConfig().getConfigurationSection("coreBlocks").getKeys(false);
            for(String keyCores : cores)
                this.coreBlocks.put(keyCores, this.getConfig().getString("coreBlocks." + keyCores));

        } catch (NullPointerException e) {
            System.out.println(ChatColor.RED + "There is no existing portals");
        }
    }

    public List<String> getParticulePositions()
    {
        List<String> particules = new ArrayList<>();
        for(String key : this.config.keySet())
        {
            Map map = this.config.get(key);
            if((boolean)map.get("isActive"))
                particules.addAll((List<String>)map.get("portalPos"));
        }
        return particules;
    }

    public void createPortalConfig(String portalName)
    {
        Map portalMap = new HashMap<>();
        this.config.put(portalName, portalMap);
        this.getConfig().set("portals." + portalName, portalMap);
        this.saveConfig();
    }

    public void addPortalNumber(int number)
    {
        this.portalNumbers.add(number);
        this.getConfig().set("portalNumbers", portalNumbers);
        this.saveConfig();
    }

    public void addPortalBlockCore(String portalName, int x, int y, int z)
    {
        Map portalMap = config.get(portalName);
        String position = x + ", " + y + ", " + z;
        portalMap.put("coreBlock", position);
        this.config.put(portalName, portalMap);
        this.coreBlocks.put(position, portalName);
        this.getConfig().set("portals." + portalName + ".coreBlock", x + ", " + y + ", " + z);
        this.getConfig().set("coreBlocks." + position, portalName);
        this.saveConfig();
    }

    public void addPortalBlockCoords(String portalName, List<String> coords)
    {
        Map portalMap = config.get(portalName);
        portalMap.put("blockCoords", coords);
        this.config.put(portalName, portalMap);
        this.getConfig().set("portals." + portalName + ".blockCoords", coords);
        this.saveConfig();
    }

    public void setPortalStatus(String portalName, boolean status)
    {
        Map portalMap = config.get(portalName);
        portalMap.put("isActive", status);
        this.config.put(portalName, portalMap);
        this.getConfig().set("portals." + portalName + ".isActive", status);
        this.saveConfig();
    }

    public boolean isPortalActive(String portalName)
    {
        return (boolean)this.config.get(portalName).get("isActive");
    }

    public String getPortalWorldName(String portalName)
    {
        return (String)this.config.get(portalName).get("targetWorld");
    }

    public void addParticulePositions(String portalName, List<String> coords)
    {
        Map portalMap = config.get(portalName);
        portalMap.put("portalPos", coords);
        this.config.put(portalName, portalMap);
        this.getConfig().set("portals." + portalName + ".portalPos", coords);
        this.saveConfig();
    }

    public void removePortal(String portalName)
    {
        String coreCoord = (String)this.config.get(portalName).get("coreBlock");
        this.coreBlocks.remove(coreCoord);
        this.config.remove(portalName);
        for(int i = 0; i < this.portalNumbers.size(); ++i)
        {
            if(this.portalNumbers.get(i) == Integer.parseInt(portalName.split("portal")[1]))
            {
                this.portalNumbers.remove(i);
                break;
            }
        }
        System.out.println(this.portalNumbers);
        this.getConfig().set("portalNumbers", this.portalNumbers);
        this.getConfig().set("portals." + portalName, null);
        this.getConfig().set("coreBlocks." + coreCoord, null);
        this.saveConfig();
    }

    public void createWorld(String worldName)
    {
        WorldCreator worldCreator = new WorldCreator(worldName);
        worldCreator.environment(World.Environment.NORMAL);
        worldCreator.type(WorldType.NORMAL);
        worldCreator.createWorld();
    }

    public void addWorld(String world)
    {
        this.worlds.add(world);
        List<String> list = this.getConfig().getStringList("worlds");
        list.add(world);
        this.getConfig().set("worlds", list);
        this.saveConfig();
    }

    public void setWorldToPortal(String portalName, String worldName)
    {
        Map portalMap = this.config.get(portalName);
        portalMap.put("targetWorld", worldName);
        this.getCoreBlockFromPortalName(portalName).setType(Material.GILDED_BLACKSTONE);
        this.config.put(portalName, portalMap);
        this.getConfig().set("portals." + portalName + ".targetWorld", worldName);
        this.saveConfig();
    }

    public void removeWorldFromPortal(String portalName)
    {
        Map portalMap = this.config.get(portalName);
        portalMap.remove("targetWorld");
        this.getCoreBlockFromPortalName(portalName).setType(Material.BLACKSTONE);
        this.config.put(portalName, portalMap);
        this.getConfig().set("portals." + portalName + ".targetWorld", null);
        this.saveConfig();
    }

    public int getNextPortalNumber()
    {
        return this.portalNumbers.isEmpty() ? 1 : this.portalNumbers.get(this.portalNumbers.size()-1)+1;
    }

    public int getNextWorldNumber()
    {
        return this.worlds.size()+1;
    }

    public Map<String, Map> getPortalMap()
    {
        return this.config;
    }

    public Map<String, String> getCoreBlocks()
    {
        return this.coreBlocks;
    }

    public Block getCoreBlockFromPortalName(String portalName)
    {
        String[] coords = ((String)this.config.get(portalName).get("coreBlock")).split(", ");
        int x = Integer.parseInt(coords[0]), y = Integer.parseInt(coords[1]), z = Integer.parseInt(coords[2]);
        return Bukkit.getWorlds().get(0).getBlockAt(x, y, z);
    }

    public boolean isBlockPortalCore(Block block)
    {
        int x = block.getX(), y = block.getY(), z = block.getZ();
        return this.coreBlocks.containsKey(x + ", " + y + ", " + z);
    }

    public String getPortalNameFromCore(Block block)
    {
        int x = block.getX(), y = block.getY(), z = block.getZ();
        return this.coreBlocks.get(x + ", " + y + ", " + z);
    }

    public String getPortalNameFromBlock(Block block)
    {
        int x = block.getX(), y = block.getY(), z = block.getZ();
        for(String portalKeys : this.config.keySet())
        {
            List<String> blockCoords = (List<String>)this.config.get(portalKeys).get("blockCoords");
            for(String coords : blockCoords)
            {
                String coord = x + ", " + y + ", " + z;
                if(coords.equals(coord))
                    return portalKeys;
            }
        }
        return null;
    }
}
