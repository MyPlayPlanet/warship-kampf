package net.myplayplanet.wsk.objects;

import lombok.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.HashMap;
import java.util.UUID;

@Setter
@Getter
@EqualsAndHashCode(of = "uuid")
@RequiredArgsConstructor
public class WSKPlayer {
    private Team team;
    private final UUID uuid;
    boolean captain;

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    // Static instance stuff down below

    private static HashMap<UUID, WSKPlayer> instances = new HashMap<>();

    public static WSKPlayer getPlayer(Player player) {
        return instances.get(player.getUniqueId());
    }

    public static void handle(PlayerJoinEvent event) {
        UUID uuid = event.getPlayer().getUniqueId();
        if(!instances.containsKey(uuid))
            instances.put(uuid, new WSKPlayer(uuid));
    }
}
