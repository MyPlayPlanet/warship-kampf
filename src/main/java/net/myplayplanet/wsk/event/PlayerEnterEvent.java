package net.myplayplanet.wsk.event;

import lombok.Getter;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.event.HandlerList;

@Getter
public class PlayerEnterEvent extends ArenaEvent {

    private final Team boardedTeam;
    private final WSKPlayer player;

    public PlayerEnterEvent(Arena arena, Team boardedTeam, WSKPlayer player) {
        super(arena);
        this.boardedTeam = boardedTeam;
        this.player = player;
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
