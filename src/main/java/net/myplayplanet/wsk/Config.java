package net.myplayplanet.wsk;

import net.myplayplanet.wsk.util.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;

public class Config {

    private final static File configFile = new File(JavaPlugin.getPlugin(WSK.class).getDataFolder(), "config.yml");
    private static YamlConfiguration cfg;

    public static void load() {
        if (!configFile.exists() || !configFile.isFile()) {
            configFile.getParentFile().mkdirs();
            try {
                configFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
        }

        cfg = YamlConfiguration.loadConfiguration(configFile);
        cfg.options().copyDefaults(true);

        cfg.addDefault("default_arena", "arena.json");
        cfg.addDefault("setup", true);
        cfg.addDefault("auto_remove_ship", false);

        cfg.addDefault("factor.min", 0.4);
        cfg.addDefault("factor.max", 1);

        cfg.addDefault("restart_after_fight", true);

        cfg.addDefault("count_blocks_async", false);

        try {
            cfg.save(configFile);
        } catch (IOException e) {
            Logger.ERROR.log("Could not save config.yml");
            e.printStackTrace();
        }
    }

    public static double getMaxFactor() {
        return cfg.getDouble("factor.max");
    }

    public static double getMinFactor() {
        return cfg.getDouble("factor.min");
    }

    public static boolean isSetup() {
        return cfg.getBoolean("setup", true);
    }

    public static String getDefaultArena() {
        return cfg.getString("default_arena", "arena");
    }

    public static boolean isAutoRemoveShip() {
        return cfg.getBoolean("auto_remove_ship", false);
    }

    public static boolean isRestartAfterFight() {
        return cfg.getBoolean("restart_after_fight", true);
    }

    public static boolean isCountBlocksAsync() {
        return cfg.getBoolean("count_blocks_async", true);
    }
}
