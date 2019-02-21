package net.myplayplanet.wsk.objects.scoreboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.event.TeamAddmemberArenaEvent;
import net.myplayplanet.wsk.event.TeamRemovememberArenaEvent;
import net.myplayplanet.wsk.util.AsyncUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

@RequiredArgsConstructor
@Getter
public class Sidebar implements Listener {
    private final Arena arena;
    private final Scoreboard scoreboard;
    private Objective currentObjective;
    @Setter
    private ObjectiveWorker worker = new SetupSidebar();

    public void updateScoreboard() {
        cleanScoreboard();
        worker.editObjective(arena, currentObjective);
    }

    public void cleanScoreboard() {
        currentObjective = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        if (currentObjective != null)
            currentObjective.unregister();

        currentObjective = scoreboard.registerNewObjective("obj", "dummy", "§eInfo");
        currentObjective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeamAddMember(TeamAddmemberArenaEvent event) {
       updateLater();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onTeamRemoveMember(TeamRemovememberArenaEvent event) {
        updateLater();
    }

    public void updateLater() {
        AsyncUtil.runSync(() -> updateScoreboard());
    }
}
