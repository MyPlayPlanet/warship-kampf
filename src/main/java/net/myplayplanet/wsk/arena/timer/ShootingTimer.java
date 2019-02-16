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
        } else if (seconds == 60) {
            Bukkit.broadcastMessage(WSK.PREFIX + "In 60 Sekunden ist das Entern erlaubt");
        } else if (seconds == 30) {
            Bukkit.broadcastMessage(WSK.PREFIX + "In 30 Sekunden ist das Entern erlaubt");
        } else if (seconds == 10) {
            Bukkit.broadcastMessage(WSK.PREFIX + "In 10 Sekunden ist das Entern erlaubt");
        } else if (seconds == 9) {
            Bukkit.broadcastMessage(WSK.PREFIX + "In 9 Sekunden ist das Entern erlaubt");
        } else if (seconds == 8) {
            Bukkit.broadcastMessage(WSK.PREFIX + "In 8 Sekunden ist das Entern erlaubt");
        }  else if (seconds == 7) {
            Bukkit.broadcastMessage(WSK.PREFIX + "In 7 Sekunden ist das Entern erlaubt");
        }  else if (seconds == 6) {
            Bukkit.broadcastMessage(WSK.PREFIX + "In 6 Sekunden ist das Entern erlaubt");
        }  else if (seconds == 5) {
            Bukkit.broadcastMessage(WSK.PREFIX + "In 5 Sekunden ist das Entern erlaubt");
        }  else if (seconds == 4) {
            Bukkit.broadcastMessage(WSK.PREFIX + "In 4 Sekunden ist das Entern erlaubt");
        }  else if (seconds == 3) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cIn 3 Sekunden ist das Entern erlaubt");
        }  else if (seconds == 2) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cIn 2 Sekunden ist das Entern erlaubt");
        }  else if (seconds == 1) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cIn 1 Sekunde ist das Entern erlaubt");
        }   else if (seconds == 0) {
            Bukkit.getPluginManager().callEvent(new ArenaStateChangeEvent(arena.getState(), ArenaState.ENTER, arena));
        }

        super.run();
    }

    public ShootingTimer(Arena arena) {
        super(arena);
        seconds = 600;
    }
}
