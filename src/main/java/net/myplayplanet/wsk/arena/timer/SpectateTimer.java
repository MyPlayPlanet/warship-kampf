package net.myplayplanet.wsk.arena.timer;

import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import org.bukkit.Bukkit;

public class SpectateTimer extends Timer {

    @Override
    public void run() {
        if (seconds == 60)
            Bukkit.broadcastMessage(WSK.PREFIX + "Die Arena schließt in 60 Sekunden");

        if (seconds == 3) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDie Arena schließt in 3 Sekunden");
        } else if (seconds == 2) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDie Arena schließt in 2 Sekunden");
        } else if (seconds == 1) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDie Arena schließt in 1 Sekunde");
        } else if (seconds == 0) {
            Bukkit.broadcastMessage(WSK.PREFIX + "§cDie Arena schließt");

        }

        super.run();
    }

    public SpectateTimer(Arena arena) {
        super(arena);
        seconds = 60;
    }
}
