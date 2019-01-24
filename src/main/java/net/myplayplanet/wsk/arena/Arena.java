package net.myplayplanet.wsk.arena;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.TeamProperties;
import org.bukkit.World;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class Arena {

    private final File config;
    private ArenaConfig arenaConfig;
    private World gameWorld;
    private ArenaState state = ArenaState.IDLE;
    private List<Team> teams;

    public Arena(File config) {
        Preconditions.checkArgument(!config.exists(), "config does not exist");
        this.config = config;
        arenaConfig = ArenaConfig.loadFromFile(config);

        // init teams
        teams = arenaConfig.getTeams().stream().map(tp -> new Team(tp, this)).collect(Collectors.toList());
    }
}
