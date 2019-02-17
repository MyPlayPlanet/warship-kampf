package net.myplayplanet.wsk.arena.timer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.arena.Arena;
import org.bukkit.scheduler.BukkitRunnable;

@RequiredArgsConstructor
public abstract class Timer extends BukkitRunnable {

    @Getter
    protected int seconds = 1260;
    protected final Arena arena;

    @Override
    public void run() {
        seconds--;
    }
}
