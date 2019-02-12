package net.myplayplanet.wsk.objects;

import lombok.Getter;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

@Getter
public class ScoreboardManager {

    private static ScoreboardManager instance = new ScoreboardManager();
    private Scoreboard scoreboard;
    private Team guestTeam;

    private ScoreboardManager() {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();

        Arena arena = ArenaManager.getInstance().getCurrentArena();
        if (arena != null) {
            scoreboard.getTeams().forEach(Team::unregister);
            arena.getTeams().forEach((t) -> {
                Team team = scoreboard.getTeam(t.getProperties().getName());
                if (team != null)
                    team.unregister();

                team = scoreboard.registerNewTeam(t.getProperties().getName());
                team.setPrefix(t.getProperties().getColorCode());
            });
            guestTeam  = scoreboard.getTeam("9999Guest");
            if(guestTeam != null)
                guestTeam.unregister();

            guestTeam = scoreboard.registerNewTeam("9999Guest");
            guestTeam.setPrefix("§7");
        }
    }

    public static ScoreboardManager getInstance() {
        return instance;
    }

    public void handleJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        guestTeam.addEntry(player.getName());
    }

    public void handleQuitEvent(PlayerQuitEvent event) {
        removeEntry(event.getPlayer().getName());
    }

    public void playerAddToTeam(net.myplayplanet.wsk.objects.Team team, WSKPlayer player) {
        removeEntry(player.getPlayer().getName());
        scoreboard.getTeam(team.getProperties().getName()).addEntry(player.getPlayer().getName());
    }

    public void removeEntry(String playerName) {
        scoreboard.getTeams().forEach(t -> {
            if(t.getEntries().contains(playerName))
                t.removeEntry(playerName);
        });
    }
}
