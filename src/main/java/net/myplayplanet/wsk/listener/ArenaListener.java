package net.myplayplanet.wsk.listener;

import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.event.TeamAddmemberArenaEvent;
import net.myplayplanet.wsk.objects.ScoreboardManager;
import net.myplayplanet.wsk.objects.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ArenaListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onTeamAddmember(TeamAddmemberArenaEvent event) {
        Team team = event.getTeam();

        Player player = event.getPlayer().getPlayer();
        if(team.getMembers().size() == 0) {
            Bukkit.broadcastMessage(WSK.PREFIX + team.getProperties().getColorCode() + player.getName() + " §eist nun Kapitän von " +
                    team.getProperties().getName());
        } else {
            Bukkit.broadcastMessage(WSK.PREFIX + team.getProperties().getColorCode() + player.getName() + " §eist nun Mitglied von " +
                    team.getProperties().getName());
        }

        player.setDisplayName(team.getProperties().getColorCode() + player.getDisplayName());

        ScoreboardManager.getInstance().playerAddToTeam(team, event.getPlayer());
    }
}
