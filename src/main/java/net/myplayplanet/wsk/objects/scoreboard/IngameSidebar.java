package net.myplayplanet.wsk.objects.scoreboard;

import net.myplayplanet.wsk.arena.Arena;
import org.bukkit.scoreboard.Objective;

public class IngameSidebar implements ObjectiveWorker {
    @Override
    public void editObjective(Arena arena, Objective objective) {
        if (arena.getState().isInGame()) {
            int entries = arena.getTeams().size() * 2 - 1;
            StringBuilder empty = new StringBuilder(" ");

           // TODO
        }
    }
}
