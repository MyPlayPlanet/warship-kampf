package net.myplayplanet.wsk.util;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.data.BlockData;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class BlockProcessor {


    // Only return locs with this materials
    public static Set<Location> remain(Set<Location> locs, Set<Material> remain) {
        Set<Location> l;
        l = locs.stream().filter(loc -> remain.contains(loc.getBlock().getType())).collect(Collectors.toSet());
        return l;
    }

    public static Set<Location> remain(Set<Location> locs, Material remain) {
        Set<Location> l;
        l = locs.stream().filter(loc -> loc.getBlock().getType() == remain).collect(Collectors.toSet());
        return l;
    }

    public static Set<Location> remain(Set<Location> locs, Predicate<BlockData> remainer) {
        Set<Location> l;
        l = locs.stream().filter(loc -> remainer.test(loc.getBlock().getBlockData())).collect(Collectors.toSet());
        return l;
    }


    public static Set<Location> remove(Set<Location> locs, Set<Material> remove) {
        Set<Location> l;
        l = locs.stream().filter(loc -> !remove.contains(loc.getBlock().getType())).collect(Collectors.toSet());
        return l;
    }

    public static Set<Location> remove(Set<Location> locs, Material material) {
        Set<Location> l;
        l = locs.stream().filter(loc -> loc.getBlock().getType() != material).collect(Collectors.toSet());
        return l;
    }

    @SuppressWarnings("deprecation")
    public static Set<Location> remove(Set<Location> locs, Predicate<BlockData> remover) {
        Set<Location> l;
        l = locs.stream().filter(loc -> !remover.test(loc.getBlock().getBlockData())).collect(Collectors.toSet());
        return l;
    }

    public static Set<Location> getLocs(BlockVector3 v1, BlockVector3 v2, World w) {
        CuboidRegion cr = new CuboidRegion(v1, v2);
        Set<Location> locs = new HashSet<>();
        for (BlockVector3 v : cr) {
            locs.add(getLoc(v, w));
        }
        return locs;
    }

    public static Set<Location> getLocs(Location l1, Location l2) {
        CuboidRegion cr = new CuboidRegion(getVec(l1), getVec(l2));
        Set<Location> locs = new HashSet<>();
        World w = l1.getWorld();
        for (BlockVector3 v : cr) {
            locs.add(getLoc(v, w));
        }
        return locs;
    }

    public static Location getLoc(BlockVector3 v, World w) {
        return new Location(w, v.getX(), v.getY(), v.getZ());
    }

    public static Location getLoc(org.bukkit.util.Vector v, World w) {
        return new Location(w, v.getX(), v.getY(), v.getZ());
    }

    public static BlockVector3 getVec(Location loc) {
        return BlockVector3.at(loc.getX(), loc.getY(), loc.getZ());
    }

    public static BlockVector3 getVec(org.bukkit.util.Vector loc) {
        return BlockVector3.at(loc.getX(), loc.getY(), loc.getZ());
    }

    public static BlockVector3 getBlockVector(org.bukkit.util.Vector loc) {
        return Vector3.at(loc.getX(), loc.getY(), loc.getZ()).toBlockPoint();
    }
}
