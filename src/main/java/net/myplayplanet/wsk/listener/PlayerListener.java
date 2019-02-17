package net.myplayplanet.wsk.listener;

import lombok.AllArgsConstructor;
import net.myplayplanet.wsk.Config;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.objects.ScoreboardManager;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

@AllArgsConstructor
public class PlayerListener implements Listener {

    private final WSK wsk;


    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        player.setScoreboard(ScoreboardManager.getInstance().getScoreboard());
        player.getInventory().clear();
        player.setHealth(20);
        player.setFoodLevel(20);

        WSKPlayer.handle(event);
        ScoreboardManager.getInstance().handleJoinEvent(event);

        player.setDisplayName("§7" + player.getName() + "§r");

        Arena arena = wsk.getArenaManager().getCurrentArena();
        if (!arena.getState().isInGame())
            player.teleport(wsk.getArenaManager().getCurrentArena().getArenaConfig().getSpawn());
        else
            player.teleport(wsk.getArenaManager().getCurrentArena().getArenaConfig().getSpectatorSpawn());
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ScoreboardManager.getInstance().handleQuitEvent(event);

        WSKPlayer player = WSKPlayer.getPlayer(event.getPlayer());
        if (player.getTeam() != null)
            player.getTeam().removeMember(player);
    }

    @EventHandler
    public void onFoodLevelChange(FoodLevelChangeEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onRespawn(PlayerRespawnEvent event) {
        if(Config.isSetup())
            return;
        Arena arena = wsk.getArenaManager().getCurrentArena();

        if(arena.getState().isInGame())
            event.setRespawnLocation(arena.getArenaConfig().getSpectatorSpawn());
        else
            event.setRespawnLocation(arena.getArenaConfig().getSpawn());
    }
}
