package net.myplayplanet.wsk.listener;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;

public class WaterRemoveListener implements Listener {
    private List<AbstractMap.SimpleEntry<Location, Integer>> explBlocks;
    private List<Block> waterList;
    private BukkitTask task;

    private final JavaPlugin plugin;

    public WaterRemoveListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void start() {
        stop();
        Bukkit.getPluginManager().registerEvents(this, plugin);
        explBlocks = new ArrayList<>();
        waterList = new ArrayList<>();
        task = Bukkit.getScheduler().runTaskTimer(plugin, new Runnable() {
            @Override
            public void run() {
                waterCheck();
                removeWater();
            }
        }, 0, 20);
    }

    public void stop() {
        if (task != null) {
            task.cancel();
        }
        HandlerList.unregisterAll(this);
    }

    public void add(Location loc) {
        this.explBlocks.add(new AbstractMap.SimpleEntry<Location, Integer>(loc, 15));
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onExplode(EntityExplodeEvent e) {
        for (Block b : e.blockList()) {
            if (b.getType() != Material.WATER) {
                add(b.getLocation());
            }
        }
    }

    public void waterCheck() {
        for (int i = this.explBlocks.size() - 1; i > -1; i--) {
            if (this.explBlocks.get(i).getValue() >= 15) {
                Block b = this.explBlocks.get(i).getKey().getBlock();
                if (b.getType() == Material.WATER) {
                    this.waterList.add(b);
                    this.explBlocks.remove(i);
                } else {
                    this.explBlocks.get(i).setValue(this.explBlocks.get(i).getValue() + 1);
                }
            }
        }
    }

    private void removeWater() {
        for (int i = this.waterList.size() - 1; i > -1; i--) {
            Block current = this.waterList.get(i);
            for (Block removeBlock : getSourceOfWater(current)) {
                removeBlock.setType(Material.AIR);
            }
            if (current.getType() == Material.AIR) {
                this.waterList.remove(i);
            }
        }
    }

    private List<Block> getSourceOfWater(Block b) {
        List<Block> water = new ArrayList<Block>();
        collectBlocks(b, water, new ArrayList<Block>());
        return water;
    }

    /*
     * code by: andf54
     * https://forums.bukkit.org/threads/get-the-whole-stream-of-water-or-lava.
     * 110156/ Einige kleinere Änderungen vorgenommen
     */
    public void collectBlocks(Block anchor, List<Block> collected, List<Block> visitedBlocks) {
        if (anchor.getType() != Material.WATER)
            return;

        if (visitedBlocks.contains(anchor))
            return;
        visitedBlocks.add(anchor);
        if (anchor.getType() == Material.WATER) {
            collected.add(anchor);
        }
        if (visitedBlocks.size() > 50) {
            collected.clear();
            return;
        }
        collectBlocks(anchor.getRelative(BlockFace.UP), collected, visitedBlocks);
        collectBlocks(anchor.getRelative(BlockFace.NORTH), collected, visitedBlocks);
        collectBlocks(anchor.getRelative(BlockFace.EAST), collected, visitedBlocks);
        collectBlocks(anchor.getRelative(BlockFace.SOUTH), collected, visitedBlocks);
        collectBlocks(anchor.getRelative(BlockFace.WEST), collected, visitedBlocks);
    }
}