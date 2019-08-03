package net.myplayplanet.wsk.event;

import lombok.Getter;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import org.bukkit.event.HandlerList;

/**
 * Event gets fired if the state of an arena changes.
 * Useful for observing if the fights stops, starts, etc.
 */
@Getter
public class ArenaStateChangeEvent extends ArenaEvent {

    private final ArenaState oldState;
    private final ArenaState newState;

    public ArenaStateChangeEvent(ArenaState oldState, ArenaState newState, Arena arena) {
        super(arena);
        this.oldState = oldState;
        this.newState = newState;
    }

    public final static HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public final static HandlerList getHandlerList() {
        return handlers;
    }
}
