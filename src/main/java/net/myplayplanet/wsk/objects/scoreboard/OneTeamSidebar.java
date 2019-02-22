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

        final int memberCount = team.getMembers().size();

        int currentIndex = memberCount - 1;
        for (int i = 0; i < memberCount; i++) {
            WSKPlayer player = team.getMembers().get(i);
            objective.getScore(color + player.getPlayer().getName()).setScore(currentIndex--);
        }
    }
}
