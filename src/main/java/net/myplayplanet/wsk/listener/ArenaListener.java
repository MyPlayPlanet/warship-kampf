package net.myplayplanet.wsk.listener;

import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaManager;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.*;
import net.myplayplanet.wsk.objects.ScoreboardManager;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import net.myplayplanet.wsk.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class ArenaListener implements Listener {

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onStateChange(ArenaStateChangeEvent event) {
        event.getArena().setState(event.getNewState());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onCaptainSet(TeamCaptainSetArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        Team team = event.getTeam();
        Bukkit.broadcastMessage(WSK.PREFIX + team.getProperties().getColorCode() + player.getPlayer().getName() + " §eist nun Kapitän von " +
                team.getProperties().getName());

        player.setRole(Role.CAPTAIN);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onCaptainRemove(TeamCaptainRemoveArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        Team team = event.getTeam();
        Bukkit.broadcastMessage(WSK.PREFIX + team.getProperties().getColorCode() + player.getPlayer().getName() + " §eist nun nicht mehr Kapitän von " +
                team.getProperties().getName());

        player.setRole(Role.GUNNER);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMemberRemove(TeamRemovememberArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        Team team = event.getTeam();
        ScoreboardManager.getInstance().getScoreboard().getPlayerTeam(player.getPlayer()).removePlayer(player.getPlayer());
        ScoreboardManager.getInstance().getGuestTeam().addEntry(player.getPlayer().getName());
        player.getPlayer().setDisplayName("§7" + player.getPlayer().getName() + "§r");

        player.getPlayer().teleport(ArenaManager.getInstance().getCurrentArena().getArenaConfig().getSpawn());
        player.setRole(null);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMemberAdd(TeamAddmemberArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        Team team = event.getTeam();
        ScoreboardManager.getInstance().playerAddToTeam(team, player);
        player.getPlayer().setDisplayName(team.getProperties().getColorCode() + player.getPlayer().getName() + "§r");

        player.getPlayer().teleport(team.getProperties().getSpawn());
        player.setRole(Role.GUNNER);
    }
}