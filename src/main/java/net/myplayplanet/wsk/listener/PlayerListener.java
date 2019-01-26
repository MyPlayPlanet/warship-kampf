package net.myplayplanet.wsk.listener;

import net.myplayplanet.wsk.objects.ScoreboardManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ScoreboardManager.getInstance().handleJoinEvent(event);
    }

    @EventHandler
    public void onJoin(PlayerQuitEvent event) {
        ScoreboardManager.getInstance().handleQuitEvent(event);
    }
}
