package net.myplayplanet.wsk.arena;

import lombok.Getter;
import net.myplayplanet.wsk.Config;
import net.myplayplanet.wsk.WSK;

import java.io.File;

@Getter
public class ArenaManager {

    private final WSK wsk;
    private final File arenaFolder;
    private Arena currentArena;

    public ArenaManager(WSK wsk) {
        if (instance == null) {
            instance = this;
        }
        this.wsk = wsk;
        arenaFolder = new File(wsk.getDataFolder(), "arenas");
        if (!Config.isSetup())
            currentArena = new Arena(new File(arenaFolder, Config.getDefaultArena()));
    }

    // STATIC SINGLETON

    private static ArenaManager instance;


//    public static ArenaManager getInstance() {
//        return instance;
//
//    }
}
