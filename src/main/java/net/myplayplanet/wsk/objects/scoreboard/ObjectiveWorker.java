package net.myplayplanet.wsk.objects.scoreboard;

import net.myplayplanet.wsk.arena.Arena;
import org.bukkit.scoreboard.Objective;

public interface ObjectiveWorker {

    void editObjective(Arena arena, Objective objective);

    default int toMinutes(int seconds) {
        return Math.round(seconds / 60);
    }
}
