package net.myplayplanet.wsk.arena;

import net.myplayplanet.wsk.Config;
import net.myplayplanet.wsk.util.Logger;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class GameWorld {

    private final String templateName;
    private final String worldName;

    private final Object lock = new Object();

    private GameWorld(String templateName, boolean randomName) {
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

    public GameWorld(String templateName) {
        this(templateName, false);
    }

    public void sendPlayersBack() {
        synchronized (lock) {
            World w = Bukkit.getWorld(worldName);
            if(w == null)
                return;
            w.getEntities().stream().filter((f) -> f instanceof Player).forEach(p -> p.teleport(Config.getSpawn()));
        }
    }

    public void unload() {
        synchronized (lock) {
            World w = Bukkit.getWorld(worldName);
            if(w == null)
                return;
            for (Entity e : w.getEntities()) {
                if (e instanceof Player) {
                    e.remove();
                }
            }
            Bukkit.getWorlds().remove(w);
            Bukkit.unloadWorld(w, false);
        }
    }

    public void delete() {
        synchronized (lock) {
            File file = new File(worldName);
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
            //TODO gameworld path from
            File newFile = new File("plugins/WSK/arenas/" + templateName);
            File file = new File(worldName);

            try {
                FileUtils.copyDirectory(newFile, file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            World world = Bukkit.createWorld(new WorldCreator(worldName));
            Bukkit.getWorlds().add(world);
        }
    }

}
