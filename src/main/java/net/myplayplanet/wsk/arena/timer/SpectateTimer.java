package net.myplayplanet.wsk.arena.timer;

import net.myplayplanet.wsk.Config;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaManager;
import org.bukkit.Bukkit;

import java.io.File;

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
            if (Config.isRestartAfterFight())
                Bukkit.spigot().restart();
            else {
                ArenaManager manager = WSK.getInstance().getArenaManager();
                manager.setCurrentArena(new Arena(new File(manager.getArenaFolder(), Config.getDefaultArena())));
            }

        }

        super.run();
    }

    public SpectateTimer(Arena arena) {
        super(arena);
        seconds = 60;
    }
}
