package net.myplayplanet.wsk.util;

import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.flags.Flags;
import com.sk89q.worldguard.protection.flags.StateFlag;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedCuboidRegion;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.ArenaStateChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class RegionUtil implements Listener {


    private ProtectedCuboidRegion region;
    private ProtectedRegion global;

    public RegionUtil(Arena arena) {


        RegionManager manager = WorldGuard.getInstance().getPlatform().getRegionContainer()
                .get(new BukkitWorld(arena.getGameWorld().getWorld()));

        if (!manager.hasRegion("__global__"))
            // WorldGuard does not create the __global__ region until a flag gets changed.
            // Unfortunately it is not possible to do the first change via the api
            // You have to use the command
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "rg flag -w  " + arena.getGameWorld().getWorldName() + "  __global__ pvp deny");

        global = manager.getRegion("__global__");


        if (!manager.hasRegion("arena"))
            manager.addRegion(new ProtectedCuboidRegion("arena", BlockProcessor.getVec(arena.getArenaConfig().getPos1()).toBlockPoint(),
                    BlockProcessor.getVec(arena.getArenaConfig().getPos2()).toBlockPoint()));

        region = (ProtectedCuboidRegion) manager.getRegion("arena");

        global.setFlag(Flags.DENY_MESSAGE, WSK.PREFIX + "§cDas darfst du hier nicht");
        global.setFlag(Flags.INVINCIBILITY, StateFlag.State.ALLOW);
        global.setFlag(Flags.ITEM_PICKUP, StateFlag.State.DENY);
        global.setFlag(Flags.FIRE_SPREAD, StateFlag.State.DENY);

        global.setFlag(Flags.BLOCK_BREAK, StateFlag.State.DENY);
        global.setFlag(Flags.BLOCK_PLACE, StateFlag.State.DENY);
        global.setFlag(Flags.CHEST_ACCESS, StateFlag.State.DENY);

        global.setFlag(Flags.PVP, StateFlag.State.DENY);

        // Edit region region

        // Correct positions
        region.setMinimumPoint(BlockProcessor.getVec(arena.getArenaConfig().getPos1()).toBlockPoint());
        region.setMaximumPoint(BlockProcessor.getVec(arena.getArenaConfig().getPos2()).toBlockPoint());


        region.setFlag(Flags.TNT, StateFlag.State.DENY);
        region.setFlag(Flags.INVINCIBILITY, StateFlag.State.ALLOW);
        region.setFlag(Flags.FIRE_SPREAD, StateFlag.State.DENY);
        region.setFlag(Flags.EXIT, StateFlag.State.DENY);
        region.setFlag(Flags.BUILD, StateFlag.State.DENY);
        region.setFlag(Flags.BLOCK_BREAK, StateFlag.State.DENY);
        region.setFlag(Flags.CHEST_ACCESS, StateFlag.State.ALLOW);
        region.setFlag(Flags.PVP, StateFlag.State.DENY);
        region.setFlag(Flags.EXIT_VIA_TELEPORT, StateFlag.State.ALLOW);
        region.setFlag(Flags.BLOCK_PLACE, StateFlag.State.DENY);

        region.setFlag(Flags.EXIT_DENY_MESSAGE, WSK.PREFIX + "§cDu darfst die Arena nicht verlassen");

        region.setPriority(1);

        Bukkit.getPluginManager().registerEvents(this, JavaPlugin.getPlugin(WSK.class));
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onArenaChange(ArenaStateChangeEvent event) {
        if (event.getNewState() == ArenaState.SHOOTING) {
            region.setFlag(Flags.TNT, StateFlag.State.ALLOW);
            region.setFlag(Flags.INVINCIBILITY, StateFlag.State.DENY);
            region.setFlag(Flags.FIRE_SPREAD, StateFlag.State.ALLOW);

            // Allow world interaction
            region.setFlag(Flags.BUILD, StateFlag.State.ALLOW);
            region.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
            region.setFlag(Flags.BLOCK_PLACE, StateFlag.State.ALLOW);
            region.setFlag(Flags.PVP, StateFlag.State.ALLOW);

            region.setFlag(Flags.ITEM_PICKUP, StateFlag.State.ALLOW);
            region.setFlag(Flags.ITEM_DROP, StateFlag.State.DENY);
        }
    }
}
