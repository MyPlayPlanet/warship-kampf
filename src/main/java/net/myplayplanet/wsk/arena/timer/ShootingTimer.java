package net.myplayplanet.wsk.arena.timer;

import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.ArenaStateChangeEvent;
import org.bukkit.Bukkit;

public class ShootingTimer extends Timer {

    @Override
    public void run() {
        if (seconds == 300)
            Bukkit.broadcastMessage(WSK.PREFIX + "In 5 Minuten ist das Entern erlaubt");

        if ((seconds % 10 == 0 && (seconds / 10) < 4 && (seconds / 10) > 0) || (seconds > 3 && seconds < 10) || seconds == 60)
            Bukkit.broadcastMessage(WSK.PREFIX + "In " + seconds + " Sekunden ist das Entern erlaubt");

        if (seconds == 3) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cIn 3 Sekunden ist das Entern erlaubt");
        } else if (seconds == 2) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cIn 2 Sekunden ist das Entern erlaubt");
        } else if (seconds == 1) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cIn 1 Sekunde ist das Entern erlaubt");
        } else if (seconds == 0) {
            // Last, precise calculation of blocks
            arena.getTeams().forEach(team -> team.getShip().calculateBlocks());
            Bukkit.getPluginManager().callEvent(new ArenaStateChangeEvent(arena.getState(), ArenaState.ENTER, arena));
        }
        super.run();
    }

    public ShootingTimer(Arena arena) {
        super(arena);
        seconds = 600;
    }
}
