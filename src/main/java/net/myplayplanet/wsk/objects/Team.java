package net.myplayplanet.wsk.objects;

import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.*;
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
    private final Ship ship = new Ship(this);
    private int points;
    private double maxPercentage;
    private double pointsPerPercentage;

    public void setCalculations() {
        Team thisTeam = this;
        arena.getTeams().stream().filter(t -> t != thisTeam).forEach((team) -> {
            double factor;

            double initBlocks = ship.getInitBlocks();
            double foreigenInitBlocks = team.getShip().getInitBlocks();
            if (initBlocks < foreigenInitBlocks)
                factor = initBlocks / foreigenInitBlocks;
            else
                factor = foreigenInitBlocks / initBlocks;

            if (factor < 0.4)
                factor = 0.4;
            if (factor > 1)
                factor = 1;

            if (foreigenInitBlocks < initBlocks) {
                maxPercentage = 20;
                pointsPerPercentage = Math.round(100 / factor);
            } else {
                maxPercentage = Math.round(20 / factor);
                pointsPerPercentage = 100;
            }
        });
    }

    public void addMember(WSKPlayer player) {
        Objects.requireNonNull(player);
        Preconditions.checkArgument(player.getTeam() == null, "Player must not be in a team");

        TeamAddmemberArenaEvent event = new TeamAddmemberArenaEvent(arena, this, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        if (members.size() == 0)
            player.setCaptain(true);

        members.add(event.getPlayer());
        player.setTeam(this);

        if (player.isCaptain()) {
            TeamCaptainSetArenaEvent captainArenaEvent = new TeamCaptainSetArenaEvent(arena, this, player);
            Bukkit.getPluginManager().callEvent(captainArenaEvent);
        }

        checkState(arena);
    }


    public void removeMember(WSKPlayer player) {
        Objects.requireNonNull(player);
        Preconditions.checkArgument(members.contains(player), "player must be member of team");

        TeamRemovememberArenaEvent event = new TeamRemovememberArenaEvent(arena, this, player);
        Bukkit.getPluginManager().callEvent(event);
        if (event.isCancelled())
            return;

        if (player.isCaptain()) {
            TeamCaptainRemoveArenaEvent captainArenaEvent = new TeamCaptainRemoveArenaEvent(arena, this, player);
            Bukkit.getPluginManager().callEvent(captainArenaEvent);
        }

        members.remove(event.getPlayer());
        player.setTeam(null);
        player.setCaptain(false);
        checkState(arena);
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

    private void checkState(Arena arena) {
        if (!arena.getState().isInGame()) {
            if (arena.getState() == ArenaState.IDLE) {
                for (Team team : arena.getTeams()) {
                    if (team.getMembers().size() > 0) {
                        ArenaStateChangeEvent changeEvent = new ArenaStateChangeEvent(arena.getState(), ArenaState.SETUP, arena);
                        Bukkit.getPluginManager().callEvent(changeEvent);
                        return;
                    }
                }
            } else if (arena.getState() == ArenaState.SETUP) {
                for (Team team : arena.getTeams()) {
                    if (team.getMembers().size() > 0) return;
                }
                ArenaStateChangeEvent changeEvent = new ArenaStateChangeEvent(arena.getState(), ArenaState.IDLE, arena);
                Bukkit.getPluginManager().callEvent(changeEvent);
            }
        }
    }

    public WSKPlayer getCaptain() {
        return members.stream().filter(WSKPlayer::isCaptain).collect(Collectors.toList()).get(0);
    }

    public Iterator<WSKPlayer> iterator() {
        return members.iterator();
    }
}
