package net.myplayplanet.wsk.arena.timer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.arena.Arena;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public abstract class Timer extends BukkitRunnable {

    @Getter
    protected int seconds;
    protected final Arena arena;

    @Override
    public void run() {
        seconds--;
    }

    public boolean isRunning() {
        try {
            getTaskId();
        } catch (IllegalStateException e) {
            return false;
        }
        return true;
    }
}
