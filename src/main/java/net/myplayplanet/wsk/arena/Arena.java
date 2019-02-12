package net.myplayplanet.wsk.arena;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.myplayplanet.wsk.objects.Team;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Arena {

    private final File config;
    private ArenaConfig arenaConfig;
    private GameWorld gameWorld;
    private ArenaState state = ArenaState.IDLE;
    private List<Team> teams;

    public Arena(File config) {
        Preconditions.checkArgument(config.exists(), "config does not exist");
        this.config = config;
        arenaConfig = ArenaConfig.loadFromFile(config);

        // init teams
        teams = arenaConfig.getTeams().stream().map(tp -> new Team(tp, this)).collect(Collectors.toList());

        gameWorld = new GameWorld(arenaConfig.getWorld());
    }

    public Team getTeam(String name) {
        Team team = null;

        for(Team t : teams) {
            if(t.getProperties().getName().equalsIgnoreCase(name)) {
                team = t;
                break;
            }
        }

        return team;
    }
}
