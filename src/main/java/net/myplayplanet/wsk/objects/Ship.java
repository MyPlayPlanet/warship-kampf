package net.myplayplanet.wsk.objects;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import com.sk89q.worldedit.world.block.BaseBlock;
import com.sk89q.worldedit.world.block.BlockTypes;
import com.sk89q.worldedit.world.registry.BlockMaterial;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.util.AsyncUtil;
import net.myplayplanet.wsk.util.BlockProcessor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ForkJoinPool;

@RequiredArgsConstructor
@Getter
public class Ship {

    private final Team team;
    private final Object lock = new Object();

    private double initBlocks = 0;
    private double storage = 0;


    public double getBlocks() {
        calculateBlocks();

        if (storage <= 0)
            storage = 1;
        return storage;
    }

    public void resetShip() {
        AsyncUtil.executeDependOnFawe(() -> {
            BukkitWorld world = new BukkitWorld(team.getArena().getGameWorld().getWorld());
            EditSession es = WorldEdit.getInstance().getEditSessionFactory().getEditSession(new BukkitWorld(team.getArena().getGameWorld().getWorld()), -1);
            for (BlockVector3 v : new CuboidRegion(BlockProcessor.getVec(team.getProperties().getPos1()).toBlockPoint(), BlockProcessor.getVec(team.getProperties().getPos2()).toBlockPoint())) {
                try {
                    if (v.getY() <= team.getArena().getArenaConfig().getWaterHeight())
                        es.setBlock(v, BlockTypes.WATER.getDefaultState().toBaseBlock());
                    else
                        es.setBlock(v, BlockTypes.AIR.getDefaultState().toBaseBlock());
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
            es.commit();
            es.close();
        });
    }

    private void calculateBlocks() {
        ForkJoinPool.commonPool().execute(() -> {
            if (Bukkit.getOnlinePlayers().size() <= 0)
                return;
            Set<Location> locs = getLocs();
            new BukkitRunnable() {
                @Override
                public void run() {
                    storage = filter(locs).size();
                }
            }.runTask(JavaPlugin.getPlugin(WSK.class));

        });
    }

    public void setInitBlock(Runnable finishedCallback) {
        ForkJoinPool.commonPool().execute(() -> {
            if (Bukkit.getOnlinePlayers().size() <= 0)
                return;
            Set<Location> locs = getLocs();
            new BukkitRunnable() {
                @Override
                public void run() {
                    initBlocks = filter(locs).size();
                    Bukkit.getConsoleSender().sendMessage(WSK.PREFIX + "Initial blocks @" + team.getProperties().getName() + ": " + initBlocks);
                    finishedCallback.run();
                }
            }.runTask(JavaPlugin.getPlugin(WSK.class));
        });
    }

    public void replaceObsidianAndBedrock() {
        ForkJoinPool.commonPool().execute(() -> {
            if (Bukkit.getOnlinePlayers().size() <= 0)
                return;
            Set<Location> locs = getLocs();
            new BukkitRunnable() {
                @Override
                public void run() {
                    Set<Location> filtered = BlockProcessor.toHave(locs, new HashSet<Material>() {{
                        add(Material.OBSIDIAN);
                        add(Material.BEDROCK);
                    }});
                    filtered.stream().filter(loc -> loc.getBlock().getType() == Material.OBSIDIAN).forEach(loc -> loc.getBlock().setType(Material.TNT));
                    filtered.stream().filter(loc -> loc.getBlock().getType() == Material.BEDROCK).forEach(loc -> loc.getBlock().setType(Material.SLIME_BLOCK));
                }
            }.runTask(JavaPlugin.getPlugin(WSK.class));
        });
    }

    private Set<Location> getLocs() {
        return BlockProcessor.getLocs(BlockProcessor
                .getVec(team.getProperties().getPos1()), BlockProcessor.getVec(team.getProperties().getPos2()), team.getArena().getGameWorld().getWorld());
    }

    private Set<Location> filter(Set<Location> locs) {
        return BlockProcessor.filter(locs, new HashSet<Material>() {
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
        });
    }
}
