package net.myplayplanet.wsk.objects.scoreboard;

import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.scoreboard.Objective;

@RequiredArgsConstructor
public class OneTeamSidebar implements ObjectiveWorker {

    final Team team;

    @Override
    public void editObjective(Arena arena, Objective objective) {
        objective.setDisplayName(team.getProperties().getFullname());

        String color = team.getProperties().getColorCode();

        for (WSKPlayer player : team.getMembers()) {
            objective.getScore(color + player.getPlayer().getName()).setScore((int) Math.ceil(player.getPlayer().getHealth()));
        }
    }
}
