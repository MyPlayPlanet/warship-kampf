package net.myplayplanet.wsk.objects.scoreboard;

import net.myplayplanet.wsk.arena.Arena;
import org.bukkit.scoreboard.Objective;

public class SetupSidebar implements ObjectiveWorker {

    @Override
    public void editObjective(Arena arena, Objective objective) {
        if (!arena.getState().isInGame()) {
            int entries = arena.getTeams().size() * 2 - 1;
            StringBuilder empty = new StringBuilder(" ");

            int j = 0;
            for (int i = entries; i > -1; i--) {

                if (i % 2 == 0) {
                    net.myplayplanet.wsk.objects.Team team = arena.getTeams().get(j);
                    objective.getScore(team.getProperties().getColorCode() + "Mitglieder §8» §7" + team.getMembers().size()).setScore(i);
                    j++;
                } else {
                    objective.getScore(empty.toString()).setScore(i);
                    empty.append(" ");
                }

            }
        }
    }
}
