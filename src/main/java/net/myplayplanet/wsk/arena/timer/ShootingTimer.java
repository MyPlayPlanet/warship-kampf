package net.myplayplanet.wsk.arena.timer;

import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.ArenaStateChangeEvent;
import org.bukkit.Bukkit;

public class ShootingTimer extends Timer {

    @Override
    public void run() {
        if (seconds == 300) {
            Bukkit.broadcastMessage(WSK.PREFIX + "In 5 Minuten ist das Entern erlaubt");
        } else if (seconds == 0) {
            Bukkit.getPluginManager().callEvent(new ArenaStateChangeEvent(arena.getState(), ArenaState.ENTER, arena));
        }

        super.run();
    }

    public ShootingTimer(Arena arena) {
        super(arena);
        seconds = 600;
    }
}
