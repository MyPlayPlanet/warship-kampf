package net.myplayplanet.wsk.arena.timer;

import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.ArenaStateChangeEvent;
import org.bukkit.Bukkit;

public class PrerunningTimer extends Timer {
    @Override
    public void run() {
        if (seconds == 60) {
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in einer Minute");
        } else if(seconds == 30) {
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in 30 Sekunden");
        } else if(seconds == 20) {
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in 20 Sekunden");
        } else if(seconds == 10) {
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in 10 Sekunden");
        } else if(seconds == 9) {
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in 9 Sekunden");
        } else if(seconds == 8) {
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in 8 Sekunden");
        } else if(seconds == 7) {
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in 7 Sekunden");
        } else if(seconds == 6) {
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in 6 Sekunden");
        } else if(seconds == 5) {
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in 5 Sekunden");
        } else if(seconds == 4) {
            Bukkit.broadcastMessage(WSK.PREFIX + "Der Kampf startet in 4 Sekunden");
        } else if(seconds == 3) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDer Kampf startet in 3 Sekunden");
        } else if(seconds == 2) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDer Kampf startet in 2 Sekunden");
        }  else if(seconds == 1) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDer Kampf startet in 1 Sekunde");
        }  else if (seconds == 0) {
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
