package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.commandframework.api.Completer;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.objects.WSKPlayer;
import net.myplayplanet.wsk.role.Role;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class WSKCommand {

    private final WSK wsk;

    @Command(name = "wsk")
    public void mainCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        player.sendMessage(WSK.PREFIX + "WSK v" + wsk.getDescription().getVersion() + " by Butzlabben");
        player.sendMessage(WSK.PREFIX + "Hilfe: §a/wsk help");
    }



    @Command(name = "wsk.role", permission = "wsk.role", usage = "/wsk role <Spieler> <Rolle>", description = "Setzt Rolle für einen Spieler")
    public void roleCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        if (args.getArgumentCount() != 2) {
            sender.sendMessage(WSK.PREFIX + "§c/wsk role <Spieler> <Rolle>");
            return;
        }

        Player player = Bukkit.getPlayerExact(args.getArgument(0));
        if (player == null || !player.isOnline()) {
            sender.sendMessage(WSK.PREFIX + "§cDieser Spieler ist nicht online");
            return;
        }

        WSKPlayer wskPlayer = WSKPlayer.getPlayer(player);
        if (wskPlayer.getTeam() == null) {
            sender.sendMessage(WSK.PREFIX + "§cSpieler ist nicht online");
            return;
        }

        try {
            Role role = Role.valueOf(args.getArgument(1).toUpperCase());
            wskPlayer.setRole(role);

            sender.sendMessage(WSK.PREFIX + "Rolle gesetzt");
        } catch (IllegalArgumentException e) {
            sender.sendMessage(WSK.PREFIX + "§cUnbekannte Rolle");
            return;
        }
    }

    @Completer(name = "wsk.role")
    public List<String> complete(CommandArgs args) {
        List<String> completions = new ArrayList<>();
        if (args.length() > 1)
            completions.addAll(Arrays.asList(Role.values()).stream().map(r -> r.name()).collect(Collectors.toList()));
        return completions;
    }
}
