package net.myplayplanet.wsk.listener;

import net.myplayplanet.wsk.objects.ScoreboardManager;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        WSKPlayer.handle(event);
        ScoreboardManager.getInstance().handleJoinEvent(event);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ScoreboardManager.getInstance().handleQuitEvent(event);

        WSKPlayer player = WSKPlayer.getPlayer(event.getPlayer());
        if(player.getTeam() != null)
            player.getTeam().removeMember(player);
    }
}
