package net.myplayplanet.wsk.arena;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.objects.Team;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class Invite {
    private final Team team;
    private final UUID uuid;
    private final InvitationManager manager;
    // Add 60 seconds
    private final long expires = System.currentTimeMillis() + 1000 * 60;

    protected void runTimer() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (manager.getInvites().containsValue(this)) {
                    cancel();
                }
            }
        }.runTaskLater(JavaPlugin.getPlugin(WSK.class), 20 * 60);
    }

    public boolean cancel() {
        OfflinePlayer player = Bukkit.getPlayer(uuid);
        if (player != null && ((Player) player).isOnline()) {
            ((Player) player).sendMessage(WSK.PREFIX + "Die Einladung von " + team.getProperties().getFullname() + " §7ist abgelaufen");
        }
        if (team.getCaptain() != null) {
            team.getCaptain().getPlayer().sendMessage("Die Einladung an " + player.getName() + " ist abgelaufen");
        }
        return manager.removeInvite(uuid, team);
    }
}
