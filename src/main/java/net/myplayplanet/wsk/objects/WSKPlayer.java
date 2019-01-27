package net.myplayplanet.wsk.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Setter
@Getter
@EqualsAndHashCode(exclude = "team")
public class WSKPlayer {
    private Team team;
    private UUID uuid;
    boolean captain;

    public Player getPlayer() {
        return Bukkit.getPlayer(uuid);
    }
}
