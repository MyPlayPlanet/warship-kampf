package net.myplayplanet.wsk.objects;

import lombok.Getter;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
    private Objective obj;
    private ArenaManager manager;

    private ScoreboardManager() {
    }

    public void init(WSK wsk) {
        scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        manager = wsk.getArenaManager();

        Arena arena = wsk.getArenaManager().getCurrentArena();
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

            updateSetupSidebar();
        }

        guestTeam = scoreboard.getTeam("9999Guest");
        if (guestTeam != null)
            guestTeam.unregister();

        guestTeam = scoreboard.registerNewTeam("9999Guest");
        guestTeam.setPrefix("§7");
        guestTeam.setColor(ChatColor.getByChar("7"));
    }

    public static ScoreboardManager getInstance() {
        return instance;
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

    public void updateSetupSidebar() {
        obj = scoreboard.getObjective(DisplaySlot.SIDEBAR);
        if (obj != null)
            obj.unregister();

        obj = scoreboard.registerNewObjective("obj", "dummy", "§eInfo");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        if (!manager.getCurrentArena().getState().isInGame()) {
            int entries = manager.getCurrentArena().getTeams().size() * 2 - 1;
            StringBuilder empty = new StringBuilder(" ");

            int j = 0;
            for (int i = entries; i > -1; i--) {

                if (i % 2 == 0) {
                    net.myplayplanet.wsk.objects.Team team = manager.getCurrentArena().getTeams().get(j);
                    obj.getScore(team.getProperties().getColorCode() + "Mitglieder §8» §7" + team.getMembers().size()).setScore(i);
                    j++;
                } else {
                    obj.getScore(empty.toString()).setScore(i);
                    empty.append(" ");
                }

            }
        }
    }

    public void removeEntry(String playerName) {
        scoreboard.getTeams().forEach(t -> {
            if (t.getEntries().contains(playerName))
                t.removeEntry(playerName);
        });
    }
}
