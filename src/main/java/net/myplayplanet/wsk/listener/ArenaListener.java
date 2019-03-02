package net.myplayplanet.wsk.listener;

import lombok.AllArgsConstructor;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.arena.timer.EnterAllTimer;
import net.myplayplanet.wsk.arena.timer.EnterTimer;
import net.myplayplanet.wsk.arena.timer.PrerunningTimer;
import net.myplayplanet.wsk.arena.timer.ShootingTimer;
import net.myplayplanet.wsk.event.*;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import net.myplayplanet.wsk.role.Role;
import net.myplayplanet.wsk.util.InitialCalculator;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
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
            WaterRemoveListener remover = new WaterRemoveListener(wsk);
            wsk.getServer().getPluginManager().registerEvents(remover, wsk);
            remover.start();

            InitialCalculator calculator = new InitialCalculator(arena);
            arena.getTeams().forEach(team -> team.getShip().setInitBlock(() -> calculator.readyTeam(team)));

            arena.setTimer(new ShootingTimer(arena));
        } else if (state == ArenaState.ENTER) {
            arena.setTimer(new EnterTimer(arena));
        } else if (state == ArenaState.ENTER_ALL) {
            arena.setTimer(new EnterAllTimer(arena));
        } else if (state == ArenaState.SPECTATE) {
            arena.stop();
        }

        // Run timer if game is running
        if (state.isInGame() && arena.getTimer() != null && !arena.getTimer().isRunning())
            arena.getTimer().runTaskTimer(JavaPlugin.getPlugin(WSK.class), 0, 20);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMemberDie(TeamMemberDieEvent event) {
        Team team = event.getTeam();
        event.getPlayer().setDead(true);
        if (event.getPlayer().isCaptain())
            team.addPoints(-100);

        event.getArena().getTeams().stream().filter(t -> t != team).forEach((t) -> {
            if (event.getPlayer().isCaptain())
                t.addPoints(400);
            else
                t.addPoints(300);
        });

        if (team.getAliveMembers().size() == 0)
            Bukkit.getPluginManager().callEvent(new ArenaStateChangeEvent(event.getArena().getState(), ArenaState.SPECTATE, event.getArena()));
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onCaptainSet(TeamCaptainSetArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        Team team = event.getTeam();
        Bukkit.broadcastMessage(WSK.PREFIX + team.getProperties().getColorCode() + player.getPlayer().getName() + " §7ist nun Kapitän von " +
                team.getProperties().getName());

        player.setRole(Role.CAPTAIN);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onCaptainRemove(TeamCaptainRemoveArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        Team team = event.getTeam();
        Bukkit.broadcastMessage(WSK.PREFIX + team.getProperties().getColorCode() + player.getPlayer().getName() + " §7ist nun nicht mehr Kapitän von " +
                team.getProperties().getName());

        player.setRole(Role.GUNNER);

        // Set ship to water
        team.getShip().resetShip();
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMemberRemove(TeamRemoveMemberArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        if (event.getArena().getScoreboardManager().getScoreboard().getPlayerTeam(player.getPlayer()) != null)
            event.getArena().getScoreboardManager().getScoreboard().getPlayerTeam(player.getPlayer()).removePlayer(player.getPlayer());
        event.getArena().getScoreboardManager().getGuestTeam().addEntry(player.getPlayer().getName());

        player.getPlayer().setDisplayName("§7" + player.getPlayer().getName() + "§r");

        player.getPlayer().teleport(wsk.getArenaManager().getCurrentArena().getArenaConfig().getSpawn());
        player.setRole(null);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onMemberAdd(TeamAddMemberArenaEvent event) {
        WSKPlayer player = event.getPlayer();
        Team team = event.getTeam();

        event.getArena().getScoreboardManager().playerAddToTeam(team, player);

        player.getPlayer().setDisplayName(team.getProperties().getColorCode() + player.getPlayer().getName() + "§7");

        player.getPlayer().teleport(team.getProperties().getSpawn());
        player.setRole(Role.GUNNER);
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onTeamWin(TeamWinEvent event) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendTitle(event.getTeam().getProperties().getFullname() + " §7hat gewonnen", "", 3 * 20, 5 * 20, 3 * 20);
        }
    }
}