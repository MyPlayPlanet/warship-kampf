package net.myplayplanet.wsk.arena;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import net.myplayplanet.wsk.arena.timer.Timer;
import net.myplayplanet.wsk.event.TeamDrawEvent;
import net.myplayplanet.wsk.event.TeamLoseEvent;
import net.myplayplanet.wsk.event.TeamWinEvent;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.scoreboard.ScoreboardManager;
import net.myplayplanet.wsk.util.ColorConverter;
import net.myplayplanet.wsk.util.Logger;
import net.myplayplanet.wsk.util.RegionUtil;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Arena {

    private final File config;
    private ArenaConfig arenaConfig;
    private GameWorld gameWorld;
    @Setter
    private ArenaState state = ArenaState.IDLE;
    private List<Team> teams;
    @Setter
    private Timer timer;
    private RegionUtil util;
    private ScoreboardManager scoreboardManager;

    public Arena(File config) {
        Preconditions.checkArgument(config.exists(), "config does not exist");
        this.config = config;
        arenaConfig = ArenaConfig.loadFromFile(config);

        // init teams
        teams = arenaConfig.getTeams().stream().map(tp -> new Team(tp, this)).collect(Collectors.toList());

        if (teams.size() < 2) {
            Logger.ERROR.log("Not enough teams set");
            throw new IllegalStateException("There must be 2 teams or more");
        }

        gameWorld = new GameWorld(arenaConfig.getWorld(), this);
        gameWorld.load();

        World world = Bukkit.getWorld(arenaConfig.getWorld());
        if (world != null) {
            arenaConfig.getSpawn().setWorld(Bukkit.getWorld(arenaConfig.getWorld()));
            arenaConfig.getSpectatorSpawn().setWorld(Bukkit.getWorld(arenaConfig.getWorld()));

            teams.forEach(t -> t.getProperties().getSpawn().setWorld(world));

            // Set spawn blocks
            teams.forEach((team) -> team.getProperties().getSpawn().getBlock().getRelative(BlockFace.DOWN)
                    .setType(ColorConverter.getConcreteFromColorCode(team.getProperties().getColorCode())));

            util = new RegionUtil(this);
        } else
            Logger.ERROR.log("World could not be loaded");

        scoreboardManager = new ScoreboardManager(this);
    }

    /**
     * Determines winning team and then executes stop(winningTeam);
     */
    public void stop() {
        List<Team> localList = new ArrayList<>(teams);
        localList.forEach(Team::setPoints);
        localList.sort(Team::compareTo);
        if (localList.get(0).getPoints() == localList.get(1).getPoints())
            stop(null);
        stop(localList.get(0));
    }

    public void stop(Team winningTeam) {
        timer = null;

        if (winningTeam != null) {
            Bukkit.getPluginManager().callEvent(new TeamWinEvent(this, winningTeam));
            teams.stream().filter(team -> team == winningTeam).forEach(team -> Bukkit.getPluginManager().callEvent(new TeamLoseEvent(this, team)));
        } else {
            teams.forEach(team -> Bukkit.getPluginManager().callEvent(new TeamDrawEvent(this, team)));
        }

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.getInventory().clear();
            player.setGameMode(GameMode.SPECTATOR);
            player.teleport(getArenaConfig().getSpectatorSpawn());
            player.getActivePotionEffects().clear();
        }
    }

    public Team getTeam(String name) {
        Team team = null;

        for (Team t : teams) {
            if (t.getProperties().getName().equalsIgnoreCase(name)) {
                team = t;
                break;
            }
        }

        return team;
    }

    @Override
    protected void finalize() throws Throwable {
        gameWorld.finalize();
    }
}
