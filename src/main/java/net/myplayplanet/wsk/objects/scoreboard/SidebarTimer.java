package net.myplayplanet.wsk.objects.scoreboard;

import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.objects.Team;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public class SidebarTimer extends BukkitRunnable {

    private final Sidebar sidebar;
    private int state = 0;
    private int seconds = 15;

    @Override
    public void run() {
        if (seconds <= 0) {
            int teamCount = sidebar.getArena().getTeams().size();
            if (state >= 0) {
                Team team = sidebar.getArena().getTeams().get(state);
                sidebar.setWorker(new OneTeamSidebar(team));
                seconds = 5;
            } else {
                seconds = 15;
                sidebar.setWorker(new FullInformationSidebar());
                sidebar.getArena().getTeams().forEach(team -> team.getShip().calculateBlocks(() -> sidebar.updateScoreboard()));
            }

            sidebar.updateScoreboard();

            ++state;
            if (state >= teamCount)
                state = -1;
        } else if (seconds == 8 && state == 0) {
            sidebar.getArena().getTeams().forEach(team -> team.getShip().calculateBlocks(() -> sidebar.updateScoreboard()));
        }
        seconds--;
    }
}
