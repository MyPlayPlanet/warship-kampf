package net.myplayplanet.wsk.util;

import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.math.Vector3;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BlockProcessor {

    public static Set<Location> filter(Set<Location> locs, Set<Material> toRemove) {
        Set<Location> l;
        l = locs.stream().filter(loc -> !toRemove.contains(loc.getBlock().getType())).collect(Collectors.toSet());
        return l;
    }

    // Only return locs with this materials
    public static Set<Location> toHave(Set<Location> locs, Set<Material> toHave) {
        Set<Location> l;
        l = locs.stream().filter(loc -> toHave.contains(loc.getBlock().getType())).collect(Collectors.toSet());
        return l;
    }

    public static Set<Location> filter(Set<Location> locs, Material material) {
        Set<Location> l;
        l = locs.stream().filter(loc -> loc.getBlock().getType() == material).collect(Collectors.toSet());
        return l;
    }

    @SuppressWarnings("deprecation")
    public static Set<Location> filter(Set<Location> locs, byte data) {
        Set<Location> l;
        l = locs.stream().filter(loc -> loc.getBlock().getData() == data).collect(Collectors.toSet());
        return l;
    }

    public static Set<Location> getLocs(Vector3 l1, Vector3 l2, World w) {
        CuboidRegion cr = new CuboidRegion(l1.toBlockPoint(), l2.toBlockPoint());
        Set<Location> locs = new HashSet<>();
        for (BlockVector3 v : cr) {
            locs.add(getLoc(v.toVector3(), w));
        }
        return locs;
    }

    public static Set<Location> getLocs(Location l1, Location l2) {
        CuboidRegion cr = new CuboidRegion(getVec(l1).toBlockPoint(), getVec(l2).toBlockPoint());
        Set<Location> locs = new HashSet<>();
        World w = l1.getWorld();
        for (BlockVector3 v : cr) {
            locs.add(getLoc(v.toVector3(), w));
        }
        return locs;
    }

    public static Location getLoc(Vector3 v, World w) {
        return new Location(w, v.getX(), v.getY(), v.getZ());
    }

    public static Vector3 getVec(Location loc) {
        return Vector3.at(loc.getX(), loc.getY(), loc.getZ());
    }

    public static Vector3 getVec(org.bukkit.util.Vector loc) {
        return Vector3.at(loc.getX(), loc.getY(), loc.getZ());
    }
}
