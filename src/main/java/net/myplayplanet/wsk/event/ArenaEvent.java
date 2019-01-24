package net.myplayplanet.wsk.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.arena.Arena;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;

@RequiredArgsConstructor
@Getter
public abstract class ArenaEvent extends Event implements Cancellable {

    private boolean cancelled;
    private final Arena arena;

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}
