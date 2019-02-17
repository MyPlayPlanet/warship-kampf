package net.myplayplanet.wsk.event;

import lombok.Getter;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.event.HandlerList;

// Gets fired when captain gets set of a team
@Getter
public class TeamCaptainSetArenaEvent extends ArenaEvent {

    private final Team team;
    private final WSKPlayer player;

    public TeamCaptainSetArenaEvent(Arena arena, Team team, WSKPlayer player) {
        super(arena);
        this.team = team;
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
