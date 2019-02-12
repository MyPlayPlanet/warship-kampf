package net.myplayplanet.wsk.objects;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.event.TeamAddmemberArenaEvent;
import net.myplayplanet.wsk.event.TeamCaptainRemoveArenaEvent;
import net.myplayplanet.wsk.event.TeamCaptainSetArenaEvent;
import net.myplayplanet.wsk.event.TeamDelmemberArenaEvent;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class Team implements Iterable<WSKPlayer> {

    private List<WSKPlayer> members = new ArrayList<>();
    private final TeamProperties properties;
    private final Arena arena;

    public void addMember(WSKPlayer player) {
        Objects.requireNonNull(player);
        Preconditions.checkArgument(player.getTeam() != null, "Player must not be in a team");

        TeamAddmemberArenaEvent event = new TeamAddmemberArenaEvent(arena, this, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        members.add(event.getPlayer());
        player.setTeam(this);
        if (members.size() == 0)
            player.setCaptain(true);

        TeamCaptainSetArenaEvent captainArenaEvent = new TeamCaptainSetArenaEvent(arena, this, player);
        Bukkit.getPluginManager().callEvent(captainArenaEvent);

        player.getPlayer().setDisplayName(properties.getColorCode() + player.getPlayer().getDisplayName());

        ScoreboardManager.getInstance().playerAddToTeam(this, event.getPlayer());
    }

    public void removeMember(WSKPlayer player) {
        Objects.requireNonNull(player);
        Preconditions.checkArgument(members.contains(player), "player must be member of team");

        TeamDelmemberArenaEvent event = new TeamDelmemberArenaEvent(arena, this, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        TeamCaptainRemoveArenaEvent captainArenaEvent = new TeamCaptainRemoveArenaEvent(arena, this, player);
        Bukkit.getPluginManager().callEvent(captainArenaEvent);

        members.remove(event.getPlayer());
        player.setTeam(null);
        player.setCaptain(false);
    }

    public void setCaptain(WSKPlayer player) {
        Objects.requireNonNull(player);
        Preconditions.checkArgument(members.contains(player), "player must be member of team");

        WSKPlayer captain = getCaptain();
        Preconditions.checkArgument(!captain.equals(player), "player is already captain");

        if (captain != null) {
            TeamCaptainRemoveArenaEvent captainRemoveArenaEvent = new TeamCaptainRemoveArenaEvent(arena, this, captain);
            Bukkit.getPluginManager().callEvent(captainRemoveArenaEvent);
            captain.setCaptain(false);
        }

        player.setCaptain(true);
        TeamCaptainSetArenaEvent captainSetArenaEvent = new TeamCaptainSetArenaEvent(arena, this, player);
        Bukkit.getPluginManager().callEvent(captainSetArenaEvent);
    }

    public WSKPlayer getCaptain() {
        return members.stream().filter(WSKPlayer::isCaptain).collect(Collectors.toList()).get(0);
    }

    public Iterator<WSKPlayer> iterator() {
        return members.iterator();
    }
}
