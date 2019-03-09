package net.myplayplanet.wsk.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import net.myplayplanet.wsk.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Setter
@Getter
@EqualsAndHashCode(of = "uuid")
@RequiredArgsConstructor
public class WSKPlayer {

    private Team team;
    private final UUID uuid;
    private Role role;
    private boolean captain;
    private boolean dead;

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }

    public OfflinePlayer getOfflinePlayer() {
        return Bukkit.getOfflinePlayer(uuid);
    }

    public boolean isInTeam() {
        return team != null;
    }

    // Static instance stuff down below

    private static HashMap<UUID, WSKPlayer> instances = new HashMap<>();

    public static WSKPlayer getPlayer(Player player) {
        return getPlayer(player.getUniqueId());
    }

    public static void handle(PlayerJoinEvent event) {
        add(event.getPlayer());
    }

    public static void add(Player player) {
        UUID uuid = player.getUniqueId();
        add(uuid);
    }

    public static void add(UUID uuid) {
        if (!instances.containsKey(uuid))
            instances.put(uuid, new WSKPlayer(uuid));
    }

    public static WSKPlayer getPlayer(UUID uuid) {
        return instances.get(uuid);
    }

    public boolean isAlive() {
        return !isDead();
    }

    public static List<WSKPlayer> getPlayers() {
        return new ArrayList<>(instances.values());
    }
}
