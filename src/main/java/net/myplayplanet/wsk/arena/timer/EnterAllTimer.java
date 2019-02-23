package net.myplayplanet.wsk.arena.timer;

import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.ArenaStateChangeEvent;
import org.bukkit.Bukkit;

public class EnterAllTimer extends Timer {

    @Override
    public void run() {
        if (seconds == 300)
            Bukkit.broadcastMessage(WSK.PREFIX + "In 5 Minuten endet der Kampf");

        if ((seconds % 10 == 0 && (seconds / 10) < 4 && (seconds / 10) > 0) || (seconds > 3 && seconds < 10) || seconds == 60)
            Bukkit.broadcastMessage(WSK.PREFIX + "In " + seconds + " Sekunden endet der Kampf");

        if (seconds == 3) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDer Kampf endet in 3 Sekunden");
        } else if (seconds == 2) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDer Kampf endet in 2 Sekunden");
        } else if (seconds == 1) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDer Kampf endet in 1 Sekunde");
        } else if (seconds == 0) {
            Bukkit.getPluginManager().callEvent(new ArenaStateChangeEvent(arena.getState(), ArenaState.SPECTATE, arena));
        }
        super.run();
    }

    public EnterAllTimer(Arena arena) {
        super(arena);
        seconds = 300;
    }
}
