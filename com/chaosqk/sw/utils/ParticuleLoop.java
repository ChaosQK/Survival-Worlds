package com.chaosqk.sw.utils;

import com.chaosqk.sw.SWorlds;
import org.bukkit.Bukkit;
import org.bukkit.Particle;

import java.util.List;

public class ParticuleLoop {

    private SWorlds plugin;

    public ParticuleLoop(SWorlds sworlds)
    {
        this.plugin = sworlds;
    }

    public void start() {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
            List<String> list = plugin.getParticulePositions();

            if(!list.isEmpty())
            {
                for(int i = 0; i < list.size(); ++i)
                {
                    String[] pos = list.get(i).split(", ");
                    double x = (double)(Integer.parseInt(pos[0]))+0.5;
                    double y = (double)(Integer.parseInt(pos[1]))+0.5;
                    double z = (double)(Integer.parseInt(pos[2]))+0.5;

                    Bukkit.getWorld("world").spawnParticle(Particle.CLOUD, x, y, z, 0);
                }
            }
        }, 0L, 10L);
    }

}
