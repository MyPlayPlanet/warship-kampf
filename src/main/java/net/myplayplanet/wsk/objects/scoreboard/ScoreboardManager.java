package net.myplayplanet.wsk.objects.scoreboard;

import lombok.Getter;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@Getter
public class ScoreboardManager {

    private Scoreboard scoreboard;
    private Team guestTeam;
    private Sidebar sidebar;

    public ScoreboardManager(Arena arena) {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        if (arena != null) {
            scoreboard.getTeams().forEach(Team::unregister);
            arena.getTeams().forEach((t) -> {
                Team team = scoreboard.getTeam(t.getProperties().getName());
                if (team != null)
                    team.unregister();

                team = scoreboard.registerNewTeam(t.getProperties().getName());
                team.setPrefix(t.getProperties().getColorCode());
                team.setColor(ChatColor.getByChar(t.getProperties().getColorCode().charAt(1)));
            });

            sidebar = new Sidebar(arena, scoreboard);
            Bukkit.getPluginManager().registerEvents(sidebar, JavaPlugin.getPlugin(WSK.class));
        }

        guestTeam = scoreboard.getTeam("9999Guest");
        if (guestTeam != null)
            guestTeam.unregister();

        guestTeam = scoreboard.registerNewTeam("9999Guest");
        guestTeam.setPrefix("§7");
        guestTeam.setColor(ChatColor.getByChar("7"));
    }

    public void handleJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        guestTeam.addPlayer(player);
    }

    public void handleQuitEvent(PlayerQuitEvent event) {
        removeEntry(event.getPlayer().getName());
    }

    public void playerAddToTeam(net.myplayplanet.wsk.objects.Team team, WSKPlayer player) {
        scoreboard.getPlayerTeam(player.getPlayer()).removePlayer(player.getPlayer());
        scoreboard.getTeam(team.getProperties().getName()).addEntry(player.getPlayer().getName());
    }


    public void removeEntry(String playerName) {
        scoreboard.getTeams().forEach(t -> {
            if (t.getEntries().contains(playerName))
                t.removeEntry(playerName);
        });
    }
}
