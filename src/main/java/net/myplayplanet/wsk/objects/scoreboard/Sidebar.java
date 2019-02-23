package net.myplayplanet.wsk.objects.scoreboard;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.ArenaStateChangeEvent;
import net.myplayplanet.wsk.event.TeamAddmemberArenaEvent;
import net.myplayplanet.wsk.event.TeamRemovememberArenaEvent;
import net.myplayplanet.wsk.util.AsyncUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

@RequiredArgsConstructor
@Getter
public class Sidebar implements Listener {

    private final Arena arena;
    private final Scoreboard scoreboard;
    private Objective currentObjective;
    private SidebarTimer timer;
    @Setter
    private ObjectiveWorker worker = new AllTeamCountSidebar();

    public void updateScoreboard() {
        cleanScoreboard();
        worker.editObjective(arena, currentObjective);
    }

    public void cleanScoreboard() {
        if (currentObjective != null)
            currentObjective.unregister();

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

    @EventHandler
    public void onArenaStateChange(ArenaStateChangeEvent event) {
        if (event.getNewState() == ArenaState.SHOOTING) {
            setWorker(new FullInformationSidebar());
            timer = new SidebarTimer(this);
            timer.runTaskTimer(JavaPlugin.getPlugin(WSK.class), 1 ,20);
        } else if (event.getNewState() == ArenaState.SPECTATE) {
            setWorker(new FullInformationSidebar());
            if (!timer.isCancelled())
                timer.cancel();
        }
        updateLater();
    }

    public void updateLater() {
        AsyncUtil.runSync(this::updateScoreboard);
    }
}
