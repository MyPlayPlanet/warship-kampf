package net.myplayplanet.wsk.objects;

import lombok.*;
import net.myplayplanet.wsk.role.Role;
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
    private Role role;
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
        add(event.getPlayer());
    }

    public static void add(Player player) {
        UUID uuid = player.getUniqueId();
        if(!instances.containsKey(uuid))
            instances.put(uuid, new WSKPlayer(uuid));
    }
}
