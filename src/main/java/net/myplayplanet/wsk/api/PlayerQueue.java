package net.myplayplanet.wsk.api;

import lombok.Getter;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

@Getter
public class PlayerQueue implements Listener {

    private HashMap<UUID, Runnable> hashMap = new HashMap<>();

    public void onJoin(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if (hashMap.containsKey(uuid)) {
            hashMap.remove(uuid).run();
        }
    }
}
