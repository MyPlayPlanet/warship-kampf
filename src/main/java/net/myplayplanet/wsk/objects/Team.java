package net.myplayplanet.wsk.objects;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.event.TeamAddmemberArenaEvent;
import net.myplayplanet.wsk.event.TeamDelmemberArenaEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Team implements Iterable<WSKPlayer> {

    private List<WSKPlayer> members = new ArrayList<>();
    private final TeamProperties properties;
    private final Arena arena;

    public void addMember(WSKPlayer player) {
        TeamAddmemberArenaEvent event = new TeamAddmemberArenaEvent(arena, this, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;
        members.add(event.getPlayer());
        player.setTeam(this);
        if(members.size() == 0)
            player.setCaptain(true);
    }

    public void removeMember(WSKPlayer player) {
        Preconditions.checkArgument(members.contains(player), "player must be member of team");
        TeamDelmemberArenaEvent event = new TeamDelmemberArenaEvent(arena, this, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        members.remove(event.getPlayer());
        player.setTeam(null);
        player.setCaptain(false);
    }

    public Iterator<WSKPlayer> iterator() {
        return members.iterator();
    }
}
