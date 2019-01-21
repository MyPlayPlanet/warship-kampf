package net.myplayplanet.wsk.util;

import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.regions.CuboidRegion;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class BlockProcessor {
	
	public static Set<Location> filter(Set<Location> locs, Set<Material> toRemove) {
		Set<Location> l = new HashSet<>(locs.size());
		l = locs.stream().filter(loc -> !toRemove.contains(loc.getBlock().getType())).collect(Collectors.toSet());
		return l;
	}
	
	public static Set<Location> filter(Set<Location> locs, Material material) {
		Set<Location> l = new HashSet<>(locs.size());
		l = locs.stream().filter(loc -> loc.getBlock().getType() == material).collect(Collectors.toSet());
		return l;
	}
	
	@SuppressWarnings("deprecation")
	public static Set<Location> filter(Set<Location> locs, byte data) {
		Set<Location> l = new HashSet<>(locs.size());
		l = locs.stream().filter(loc -> loc.getBlock().getData() == data).collect(Collectors.toSet());
		return l;
	}
	
	public static Set<Location> getLocs(Vector l1, Vector l2, World w) {
		CuboidRegion cr = new CuboidRegion(l1, l2);
		Set<Location> locs = new HashSet<>();
		for(Vector v : cr) {
			locs.add(getLoc(v, w));
		}
		return locs;
	}

	public static Set<Location> getLocs(Location l1, Location l2) {
		CuboidRegion cr = new CuboidRegion(getVec(l1), getVec(l2));
		Set<Location> locs = new HashSet<>();
		World w = l1.getWorld();
		for(Vector v : cr) {
			locs.add(getLoc(v, w));
		}
		return locs;
	}

	public static Location getLoc(Vector v, World w) {
		return new Location(w, v.getX(), v.getBlockY(), v.getBlockZ());
	}

	public static Vector getVec(Location loc) {
		return new Vector(loc.getX(), loc.getY(), loc.getZ());
	}

	public static Vector getVec(org.bukkit.util.Vector loc) {
		return new Vector(loc.getX(), loc.getY(), loc.getZ());
	}
}
