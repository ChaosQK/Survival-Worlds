package com.chaosqk.sw.utils;

import com.chaosqk.sw.SWorlds;
import org.bukkit.Material;
import org.bukkit.block.Block;

import java.util.*;

public class PortalCreation {

    private List<Map<String, Material>> compositions = new ArrayList<>();
    private String positionning = "";
    private SWorlds plugin;
    private String portalName;
    private int portalNumber;

    private String[] positionX = new String[]{"-1, 0, 0", "1, 0, 0", "-2, 1, 0", "2, 1, 0", "-2, 2, 0", "2, 2, 0", "-2, 3, 0", "2, 3, 0", "0, 4, 0", "-1, 4, 0", "1, 4, 0"};
    private String[] positionZ = new String[]{"0, 0, -1", "0, 0, 1", "0, 1, -2", "0, 1, 2", "0, 2, -2", "0, 2, 2", "0, 3, -2", "0, 3, 2", "0, 4, 0", "0, 4, -1", "0, 4, 1"};


    public PortalCreation(Block startingBlock, SWorlds sworlds)
    {
        this.plugin = sworlds;
        initPortalComposition();
        if(isPortal(startingBlock))
        {
            Map<String, List<String>> portalPostion = new HashMap<>();
            String[] portalPosZ = new String[] {"0, 1, 0", "0, 1, 1", "0, 1, -1", "0, 2, 0", "0, 2, 1", "0, 2, -1", "0, 3, 0", "0, 3, 1", "0, 3, -1"};
            String[] portalPosX = new String[] {"0, 1, 0", "1, 1, 0", "-1, 1, 0", "0, 2, 0", "1, 2, 0", "-1, 2, 0", "0, 3, 0","1, 3, 0", "-1, 3, 0"};
            portalPostion.put("X", Arrays.asList(portalPosX));
            portalPostion.put("Z", Arrays.asList(portalPosZ));

            this.portalNumber = this.plugin.getNextPortalNumber();
            this.portalName = "portal" + portalNumber;

            this.plugin.createPortalConfig(portalName);
            this.plugin.addPortalBlockCore(portalName, startingBlock.getX(), startingBlock.getY(), startingBlock.getZ());
            this.plugin.addPortalNumber(portalNumber);
            startingBlock.setType(Material.BLACKSTONE);
            setPortal(startingBlock, portalPostion.get(positionning));
        }
    }

    private void initPortalComposition()
    {

        List<String[]> list = Arrays.asList(positionX, positionZ);
        for(int i = 0; i < list.size(); ++i)
        {
            Map<String, Material> portalComposition = new HashMap<>();
            portalComposition.put("0, 0, 0", Material.NETHERITE_BLOCK);
            for(String posZ : list.get(i))
                portalComposition.put(posZ, Material.CRYING_OBSIDIAN);
            compositions.add(portalComposition);
        }
    }

    private void setPortal(Block startBlock, List<String> XY)
    {
        List<String> portalPosList = new ArrayList<>();
        for(int i = 0; i < XY.size(); ++i)
        {
            String[] nextPos = XY.get(i).split(", ");
            int x = startBlock.getX() + Integer.parseInt(nextPos[0]);
            int y = startBlock.getY() + Integer.parseInt(nextPos[1]);
            int z = startBlock.getZ() + Integer.parseInt(nextPos[2]);
            portalPosList.add(x + ", " + y + ", " + z);
        }
        String[] blockPos = positionning.equals("X") ? positionX : positionZ;
        List<String> posList = new ArrayList<>();
        posList.add(startBlock.getX() + ", " + startBlock.getY() + ", " + startBlock.getZ());
        for(int j = 0; j < blockPos.length; ++j)
        {
            String[] nextPos = blockPos[j].split(", ");
            String x = (startBlock.getX() + Integer.parseInt(nextPos[0])) + "";
            String y = (startBlock.getY() + Integer.parseInt(nextPos[1])) + "";
            String z = (startBlock.getZ() + Integer.parseInt(nextPos[2])) + "";
            posList.add(x + ", " + y + ", " + z);
        }
        this.plugin.addPortalBlockCoords(portalName, posList);
        this.plugin.addParticulePositions(portalName, portalPosList);
        this.plugin.setPortalStatus(portalName, false);
    }

    private boolean isPortal(Block startingBlock)
    {
        boolean isCorrect = false;
        for(Map<String, Material> compos : compositions)
        {
            if(isCorrect)
                break;
            isCorrect = true;
            for(String key : compos.keySet())
            {
                if(!isCorrect)
                    break;
                String[] nextPos = key.split(", ");
                int nextX = Integer.parseInt(nextPos[0]);
                int nextY = Integer.parseInt(nextPos[1]);
                int nextZ = Integer.parseInt(nextPos[2]);

                positionning = nextX == 0 ? "Z" : "X";

                int x = startingBlock.getX();
                int y = startingBlock.getY();
                int z = startingBlock.getZ();

                Material compoMaterial = compos.get(key);

                Block block = startingBlock.getWorld().getBlockAt(x + nextX, y + nextY, z + nextZ);
                if(block.getType() != compoMaterial)
                    isCorrect = false;
            }
        }
        return isCorrect;
    }

}
