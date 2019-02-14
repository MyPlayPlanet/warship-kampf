package net.myplayplanet.wsk.arena;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import net.myplayplanet.wsk.objects.Team;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;
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

    public Arena(File config) {
        Preconditions.checkArgument(config.exists(), "config does not exist");
        this.config = config;
        arenaConfig = ArenaConfig.loadFromFile(config);

        // init teams
        teams = arenaConfig.getTeams().stream().map(tp -> new Team(tp, this)).collect(Collectors.toList());

        gameWorld = new GameWorld(arenaConfig.getWorld());
        gameWorld.load();

        World world = Bukkit.getWorld(arenaConfig.getWorld());
        if (world != null) {
            arenaConfig.getSpawn().setWorld(Bukkit.getWorld(arenaConfig.getWorld()));
            arenaConfig.getSpectatorSpawn().setWorld(Bukkit.getWorld(arenaConfig.getWorld()));

            teams.forEach(t -> t.getProperties().getSpawn().setWorld(world));
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
