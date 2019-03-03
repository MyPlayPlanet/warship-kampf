package net.myplayplanet.wsk.arena;

import lombok.Getter;
import lombok.Setter;
import net.myplayplanet.wsk.Config;
import net.myplayplanet.wsk.WSK;

import java.io.File;

@Getter
public class ArenaManager {


    private static ArenaManager instance;

    private final WSK wsk;
    private final File arenaFolder;
    @Setter
    private Arena currentArena;

    public ArenaManager(WSK wsk) {
        if (instance == null) {
            instance = this;
        }
        this.wsk = wsk;
        arenaFolder = new File(wsk.getDataFolder(), "arenas");

        if (!Config.isSetup())
            loadArena(Config.getDefaultArena());
    }

    public void loadArena(String name) {
        currentArena = new Arena(new File(arenaFolder, name));
    }
}
