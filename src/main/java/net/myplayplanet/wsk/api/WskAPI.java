package net.myplayplanet.wsk.api;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.ArenaStateChangeEvent;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

@Getter
public class WskAPI {

    private static WskAPI instance = new WskAPI();
    private final WSK wsk;

    private WskAPI() {
        wsk = JavaPlugin.getPlugin(WSK.class);
    }

    /**
     * Starts the fight in the current arena
     *
     * @throws IllegalArgumentException if arena is not in setup state
     */
    public void startFight() {
        Arena arena = wsk.getArenaManager().getCurrentArena();
        Preconditions.checkArgument(arena.getState() == ArenaState.SETUP, "arena must be in setup state");
        Bukkit.getPluginManager().callEvent(new ArenaStateChangeEvent(arena.getState(), ArenaState.PRERUNNING, arena));
    }

    /**
     * Returns an object of the current arena
     *
     * @return current arena
     */
    public Arena getCurrentArena() {
        return wsk.getArenaManager().getCurrentArena();
    }

    /**
     * Get all Teams which are available in the current arena
     *
     * @return all available teams from the current arena
     */
    public List<Team> getTeams() {
        return wsk.getArenaManager().getCurrentArena().getTeams();
    }

    /**
     * Adds a player to a team.
     *
     * @param player which player to add
     * @param team   which team to add
     * @throws IllegalArgumentException if player is in a team
     */
    public void addPlayerToTeam(Player player, Team team) {
        Preconditions.checkNotNull(player);
        Preconditions.checkNotNull(team);
        WSKPlayer wskPlayer = WSKPlayer.getPlayer(player);
        Preconditions.checkArgument(wskPlayer.getTeam() == null, "player must not be in a team");
        team.addMember(wskPlayer);
    }

    /**
     * Removes a player from its team
     *
     * @param player which should be removed from its team
     * @throws IllegalArgumentException if player is not in a team
     */
    public void removePlayerFromHisTeam(Player player) {
        Preconditions.checkNotNull(player);
        WSKPlayer wskPlayer = WSKPlayer.getPlayer(player);
        Preconditions.checkArgument(wskPlayer.getTeam() != null, "player must be in a team");
        wskPlayer.getTeam().removeMember(wskPlayer);
    }
}
