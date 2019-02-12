package net.myplayplanet.wsk.event;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;

@Getter
public class TeamAddmemberArenaEvent extends ArenaEvent {

    private final Team team;
    private final WSKPlayer player;

    public TeamAddmemberArenaEvent(Arena arena, Team team, WSKPlayer player) {
        super(arena);
        this.team = team;
        this.player = player;
    }

    public final static HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}