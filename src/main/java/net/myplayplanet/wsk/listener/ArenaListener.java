package net.myplayplanet.wsk.listener;

import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.event.TeamCaptainRemoveArenaEvent;
import net.myplayplanet.wsk.event.TeamCaptainSetArenaEvent;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ArenaListener implements Listener {


    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void captainSetCommand(TeamCaptainSetArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        Team team = event.getTeam();
        Bukkit.broadcastMessage(WSK.PREFIX + team.getProperties().getColorCode() + player.getPlayer().getName() + " §eist nun Kapitän von " +
                team.getProperties().getName());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void captainRemoveCommand(TeamCaptainRemoveArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        Team team = event.getTeam();
        Bukkit.broadcastMessage(WSK.PREFIX + team.getProperties().getColorCode() + player.getPlayer().getName() + " §eist nun nicht mehr Kapitän von " +
                team.getProperties().getName());
    }
}
