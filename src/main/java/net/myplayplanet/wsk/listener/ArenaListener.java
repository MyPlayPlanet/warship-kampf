package net.myplayplanet.wsk.listener;

import lombok.AllArgsConstructor;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.arena.timer.PrerunningTimer;
import net.myplayplanet.wsk.arena.timer.ShootingTimer;
import net.myplayplanet.wsk.event.*;
import net.myplayplanet.wsk.objects.ScoreboardManager;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import net.myplayplanet.wsk.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

@AllArgsConstructor
public class ArenaListener implements Listener {

    private final WSK wsk;

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onStateChange(ArenaStateChangeEvent event) {
        event.getArena().setState(event.getNewState());

        ArenaState state = event.getNewState();
        Arena arena = event.getArena();

        if (arena.getTimer() != null && !arena.getTimer().isCancelled())
            arena.getTimer().cancel();

        if (state == ArenaState.PRERUNNING) {
            // Teleport players and set inventory
            WSKPlayer.getPlayers().stream().filter(WSKPlayer::isInTeam).forEach((player) -> {
                player.getPlayer().teleport(player.getTeam().getProperties().getSpawn());
                player.getPlayer().setGameMode(GameMode.SURVIVAL);
                player.getPlayer().getInventory().clear();
                player.getRole().getRole().setItems(player.getPlayer());
            });

            // Replace spawn blocks
            arena.getTeams().forEach((team) -> team.getProperties().getSpawn().getBlock().getRelative(BlockFace.DOWN)
                    .setType(Material.WATER));

            // Replace obsidian and bedrock
            arena.getTeams().forEach(team -> team.getShip().replaceObsidianAndBedrock());

            arena.setTimer(new PrerunningTimer(arena));
        } else if (state == ArenaState.SHOOTING) {
            arena.getTeams().forEach(team -> team.getShip().setInitBlock(() -> {

            }));

            arena.setTimer(new ShootingTimer(arena));
        }

        // Run timer if game is running
        if (state.isInGame())
            arena.getTimer().runTaskTimer(JavaPlugin.getPlugin(WSK.class), 0, 20);
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

        // Set ship to water
        team.getShip().resetShip();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMemberRemove(TeamRemovememberArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        Team team = event.getTeam();
        ScoreboardManager.getInstance().getScoreboard().getPlayerTeam(player.getPlayer()).removePlayer(player.getPlayer());
        ScoreboardManager.getInstance().getGuestTeam().addEntry(player.getPlayer().getName());
        player.getPlayer().setDisplayName("§7" + player.getPlayer().getName() + "§r");

        player.getPlayer().teleport(wsk.getArenaManager().getCurrentArena().getArenaConfig().getSpawn());
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