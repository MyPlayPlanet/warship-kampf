package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.ArenaManager;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
@AllArgsConstructor
public class TeamCommand {

    private WSK wsk;
    @Command(name = "wsk.team.captain", usage = "/wsk team captain <Spieler>", permission = "wsk.team.captain", description = "Sets a captain of a specfic team")
    public void captainCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        if (args.getArgumentCount() != 1) {
            sender.sendMessage(WSK.PREFIX + "§c/wsk team captain <Spieler>");
            return;
        }

        Player player = Bukkit.getPlayerExact(args.getArgument(0));
        if (player == null || !player.isOnline()) {
            sender.sendMessage(WSK.PREFIX + "§cDieser Spieler ist nicht online");
            return;
        }


        WSKPlayer wskPlayer = WSKPlayer.getPlayer(player);
        if (wskPlayer.getTeam() == null) {
            sender.sendMessage(WSK.PREFIX + "§cDieser Spieler ist in keinem Team");
            return;
        }

        wskPlayer.getTeam().removeMember(wskPlayer);
        sender.sendMessage(WSK.PREFIX + "Du hast " + player.getName() + " aus seinem Team entfernt");
    }

    @Command(name = "wsk.team.remove", usage = "/wsk team remove <Spieler>", permission = "wsk.team.remove", description = "Removes a player from his team")
    public void removeCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        if (args.getArgumentCount() != 1) {
            sender.sendMessage(WSK.PREFIX + "§c/wsk team remove <Spieler>");
            return;
        }

        Player player = Bukkit.getPlayerExact(args.getArgument(0));
        if (player == null || !player.isOnline()) {
            sender.sendMessage(WSK.PREFIX + "§cDieser Spieler ist nicht online");
            return;
        }


        WSKPlayer wskPlayer = WSKPlayer.getPlayer(player);
        if (wskPlayer.getTeam() == null) {
            sender.sendMessage(WSK.PREFIX + "§cDieser Spieler ist in keinem Team");
            return;
        }

        wskPlayer.getTeam().removeMember(wskPlayer);
        sender.sendMessage(WSK.PREFIX + "Du hast " + player.getName() + " aus seinem Team entfernt");
    }

    @Command(name = "wsk.team.put", usage = "/wsk team put <Spieler> <Team>", permission = "wsk.team.put", description = "Puts a player in the specified team")
    public void putCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        if (args.getArgumentCount() != 2) {
            sender.sendMessage(WSK.PREFIX + "§c/wsk team put <Spieler> <Team>");
            return;
        }

        Player player = Bukkit.getPlayerExact(args.getArgument(0));
        if (player == null || !player.isOnline()) {
            sender.sendMessage(WSK.PREFIX + "§cDieser Spieler ist nicht online");
            return;
        }

        Team team = ArenaManager.getInstance().getCurrentArena().getTeam(args.getArgument(1));
        if (team == null) {
            sender.sendMessage(WSK.PREFIX + "§cDieses Team existiert nicht");
            return;
        }

        WSKPlayer wskPlayer = WSKPlayer.getPlayer(player);
        if (wskPlayer.getTeam() != null)
            wskPlayer.getTeam().removeMember(wskPlayer);

        team.addMember(WSKPlayer.getPlayer(player));
        sender.sendMessage(WSK.PREFIX + "Du hast " + player.getName() + " zu " + team.getProperties().getColorCode() + team.getProperties().getName());
    }
}
