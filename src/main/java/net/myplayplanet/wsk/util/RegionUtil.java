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

public class RegionUtil implements Listener {

    private final WSK wsk;
    private ProtectedRegion arena;
    private ProtectedRegion global;

    public RegionUtil(WSK wsk) {
        this.wsk = wsk;
        Arena wskArena = wsk.getArenaManager().getCurrentArena();

        RegionManager manager = WorldGuard.getInstance().getPlatform().getRegionContainer()
                .get(new BukkitWorld(wskArena.getGameWorld().getWorld()));

        if (manager.hasRegion("__global__"))
            global = manager.getRegion("__global__");
        else
            throw new IllegalStateException("No __global__ region found");


        if (!manager.hasRegion("arena"))
            manager.addRegion(new ProtectedCuboidRegion("arena", BlockProcessor.getVec(wsk.getArenaManager().getCurrentArena().getArenaConfig().getPos1()).toBlockPoint(),
                    BlockProcessor.getVec(wsk.getArenaManager().getCurrentArena().getArenaConfig().getPos2()).toBlockPoint()));

        arena = manager.getRegion("arena");

        global.setFlag(Flags.DENY_MESSAGE, WSK.PREFIX + "§cDas darfst du hier nicht");
        global.setFlag(Flags.INVINCIBILITY, StateFlag.State.DENY);
        global.setFlag(Flags.ITEM_PICKUP, StateFlag.State.DENY);
        global.setFlag(Flags.FIRE_SPREAD, StateFlag.State.DENY);

        global.setFlag(Flags.BLOCK_BREAK, StateFlag.State.DENY);
        global.setFlag(Flags.BLOCK_PLACE, StateFlag.State.DENY);
        global.setFlag(Flags.CHEST_ACCESS, StateFlag.State.DENY);

        global.setFlag(Flags.PVP, StateFlag.State.DENY);
        global.setFlag(Flags.BLOCK_PLACE, StateFlag.State.DENY);

        // Edit arena region
        arena.setFlag(Flags.TNT, StateFlag.State.DENY);
        arena.setFlag(Flags.INVINCIBILITY, StateFlag.State.ALLOW);
        arena.setFlag(Flags.FIRE_SPREAD, StateFlag.State.DENY);
        arena.setFlag(Flags.EXIT, StateFlag.State.DENY);
        arena.setFlag(Flags.BUILD, StateFlag.State.DENY);
        arena.setFlag(Flags.BLOCK_BREAK, StateFlag.State.DENY);
        arena.setFlag(Flags.CHEST_ACCESS, StateFlag.State.ALLOW);
        arena.setFlag(Flags.PVP, StateFlag.State.DENY);
        arena.setFlag(Flags.EXIT_VIA_TELEPORT, StateFlag.State.ALLOW);
        arena.setFlag(Flags.BLOCK_PLACE, StateFlag.State.DENY);

        arena.setFlag(Flags.EXIT_DENY_MESSAGE, WSK.PREFIX + "§cDu darfst die Arena nicht verlassen");

        Bukkit.getPluginManager().registerEvents(this, wsk);
    }


    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onArenaChange(ArenaStateChangeEvent event) {
        if (event.getNewState() == ArenaState.SHOOTING) {
            arena.setFlag(Flags.TNT, StateFlag.State.ALLOW);
            arena.setFlag(Flags.INVINCIBILITY, StateFlag.State.DENY);
            arena.setFlag(Flags.FIRE_SPREAD, StateFlag.State.ALLOW);

            // Allow world interaction
            arena.setFlag(Flags.BUILD, StateFlag.State.ALLOW);
            arena.setFlag(Flags.BLOCK_BREAK, StateFlag.State.ALLOW);
            arena.setFlag(Flags.BLOCK_PLACE, StateFlag.State.ALLOW);
            arena.setFlag(Flags.PVP, StateFlag.State.ALLOW);

            arena.setFlag(Flags.ITEM_PICKUP, StateFlag.State.ALLOW);
            arena.setFlag(Flags.ITEM_DROP, StateFlag.State.DENY);
        }
    }
}
