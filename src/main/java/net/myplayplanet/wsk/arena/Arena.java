package net.myplayplanet.wsk.arena;

import com.google.common.base.Preconditions;
import lombok.Getter;
import org.bukkit.World;

import java.io.File;

@Getter
public class Arena {

    private final File config;
    private ArenaConfig arenaConfig;
    private World gameWorld;
    private ArenaState state = ArenaState.IDLE;

    public Arena(File config) {
        Preconditions.checkArgument(!config.exists(), "config does not exist");
        this.config = config;
        arenaConfig = ArenaConfig.loadFromFile(config);
    }
}
