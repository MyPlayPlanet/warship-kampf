package net.myplayplanet.wsk.arena.timer;

import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.ArenaStateChangeEvent;
import org.bukkit.Bukkit;

public class PrerunningTimer extends Timer {

    @Override
    public void run() {
        if ((seconds % 10 == 0 && (seconds / 10) < 4 && (seconds / 10) > 0) || (seconds > 3 && seconds < 10) || seconds == 60)
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in " + seconds + " Sekunden");

        if (seconds == 3) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDer Kampf startet in 3 Sekunden");
        } else if (seconds == 2) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDer Kampf startet in 2 Sekunden");
        } else if (seconds == 1) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDer Kampf startet in 1 Sekunde");
        } else if (seconds == 0) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDer Kampf startet");
            Bukkit.getPluginManager().callEvent(new ArenaStateChangeEvent(arena.getState(), ArenaState.SHOOTING, arena));
        }

        // Set level so they can see the seconds
        Bukkit.getOnlinePlayers().forEach(p -> p.setLevel(seconds));
        super.run();
    }

    public PrerunningTimer(Arena arena) {
        super(arena);
        seconds = 60;
    }
}
