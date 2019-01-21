package net.myplayplanet.wsk.objects;

import com.boydti.fawe.FaweAPI;
import com.sk89q.worldedit.BlockVector;
import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.blocks.BaseBlock;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.regions.CuboidRegion;
import lombok.AllArgsConstructor;
import net.myplayplanet.wsk.util.BlockProcessor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class Ship {
    private final Team team;

    private double initBlocks = 0;
    private double storage = 0;

    public double getBlocks() {

        calculateBlocks();

        if (storage <= 0)
            storage = 1;
        return storage;
    }

    protected void resetShip() {
        new Thread(() -> {
            BukkitWorld world = new BukkitWorld(team.getArena().getGameWorld());
            EditSession es = FaweAPI.getEditSessionBuilder(world).build();
            for (BlockVector v : new CuboidRegion(BlockProcessor.getVec(team.getProperties().getPos1()), BlockProcessor.getVec(team.getProperties().getPos2()))) {
                try {
                    if (v.getY() <= team.getArena().getArenaConfig().getWaterHeight())
                        es.setBlock(v, new BaseBlock(9));
                    else
                        es.setBlock(v, new BaseBlock(0));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            es.flushQueue();
        }).start();
    }

    private void calculateBlocks() {
        new Thread(() -> {
            if (Bukkit.getOnlinePlayers().size() <= 0)
                return;
            Set<Location> locs = BlockProcessor.getLocs(BlockProcessor.getVec(team.getProperties().getPos1()), BlockProcessor.getVec(team.getProperties().getPos2()), team.getArena().getGameWorld());
            new BukkitRunnable() {
                @Override
                public void run() {
                    storage = BlockProcessor.filter(locs, new HashSet<Material>() {
                        {
                            add(Material.WATER);
                            add(Material.AIR);
                            add(Material.TNT);
                            add(Material.SLIME_BLOCK);
                            add(Material.BEDROCK);
                            add(Material.OBSIDIAN);
                            add(Material.PISTON);
                            add(Material.STICKY_PISTON);
                            add(Material.PISTON_HEAD);
                            add(Material.GRAVEL);
                            add(Material.SAND);
                            add(Material.CYAN_CONCRETE_POWDER);
                            add(Material.BLACK_CONCRETE_POWDER);
                            add(Material.BLUE_CONCRETE_POWDER);
                            add(Material.BROWN_CONCRETE_POWDER);
                            add(Material.GRAY_CONCRETE_POWDER);
                            add(Material.GREEN_CONCRETE_POWDER);
                            add(Material.LIGHT_BLUE_CONCRETE_POWDER);
                            add(Material.LIGHT_GRAY_CONCRETE_POWDER);
                            add(Material.MAGENTA_CONCRETE_POWDER);
                            add(Material.LIME_CONCRETE_POWDER);
                            add(Material.YELLOW_CONCRETE_POWDER);
                            add(Material.WHITE_CONCRETE_POWDER);
                            add(Material.PURPLE_CONCRETE_POWDER);
                            add(Material.RED_CONCRETE_POWDER);
                            add(Material.PINK_CONCRETE_POWDER);
                            add(Material.ORANGE_CONCRETE_POWDER);
                        }
                    }).size();
                }
            }.run();
        }).start();
    }

    public double getRealInitBlocks() {
        return initBlocks;
    }

    public void setInitBlock() {
        initBlocks = getBlocks();
    }
}
