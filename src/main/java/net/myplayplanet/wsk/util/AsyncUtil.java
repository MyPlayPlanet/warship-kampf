package net.myplayplanet.wsk.util;

import net.myplayplanet.wsk.Config;
import net.myplayplanet.wsk.WSK;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.concurrent.ForkJoinPool;

public class AsyncUtil {

    private AsyncUtil() {
    }

    public static void executeAsync(Runnable runnable) {
        ForkJoinPool.commonPool().execute(runnable);
    }

    public static void executeDependOnFawe(Runnable runnable) {
        if (WSK.isFawe())
            executeAsync(runnable);
        else
            new BukkitRunnable() {
                @Override
                public void run() {
                    runnable.run();
                }
            }.runTask(JavaPlugin.getPlugin(WSK.class));

    }

    public static void executeDependOnConfig(Runnable runnable) {
        if (Config.isCountBlocksAsync())
            ForkJoinPool.commonPool().execute(runnable);
        else runnable.run();
    }

    public static void runSync(Runnable runnable) {
        new BukkitRunnable() {
            @Override
            public void run() {
                runnable.run();
            }
        }.runTask(JavaPlugin.getPlugin(WSK.class));
    }
}
