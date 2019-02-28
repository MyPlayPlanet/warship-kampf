package net.myplayplanet.wsk.arena;

import lombok.Getter;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.util.Logger;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Getter
public class GameWorld {

    private final String templateName;
    private final String worldName;
    private final Arena arena;

    private final Object lock = new Object();

    private GameWorld(String templateName, boolean randomName, Arena arena) {
        this.arena = arena;
        this.templateName = templateName;
        if (!randomName)
            worldName = templateName;
        else
            worldName = UUID.randomUUID().toString();
    }

    public boolean isWorld(World world) {
        if (world == null)
            return false;
        return world.getName().equals(worldName);
    }

    public GameWorld(String templateName, Arena arena) {
        this(templateName, false, arena);
    }

    public void sendPlayersBack() {
        synchronized (lock) {
            World w = Bukkit.getWorld(worldName);
            if (w == null)
                return;
            w.getEntities().stream().filter((f) -> f instanceof Player).forEach(p -> ((Player) p).kickPlayer(WSK.PREFIX + "§cInaktivität"));
        }
    }

    public void unload() {
        synchronized (lock) {
            World w = Bukkit.getWorld(worldName);
            if (w == null)
                return;
            Bukkit.getWorlds().remove(w);
            Bukkit.unloadWorld(w, false);
        }
    }

    public void delete() {
        synchronized (lock) {
            File file = new File(Bukkit.getWorldContainer(), worldName);
            FileUtils.deleteQuietly(file);
            if (file.exists() || file.isDirectory()) {
                Logger.ERROR.log("Could not delete old world!");
                Logger.WARN.log("Maybe the old world will be used!");
                file.delete();
            }
        }
    }

    public void load() {
        synchronized (lock) {
            File newFile = new File(WSK.getInstance().getDataFolder(), "arenas/" + templateName);
            File file = new File(Bukkit.getWorldContainer(), worldName);

            try {
                FileUtils.copyDirectory(newFile, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            World world = Bukkit.createWorld(new WorldCreator(worldName));
            Bukkit.getWorlds().add(world);
        }
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }

    @Override
    protected void finalize() {
        unloadCompletely();
    }

    public void unloadCompletely() {
        sendPlayersBack();
        unload();
        delete();
    }
}
