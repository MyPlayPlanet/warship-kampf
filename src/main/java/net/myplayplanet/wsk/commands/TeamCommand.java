package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.commandframework.api.Completer;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class TeamCommand {

    private WSK wsk;

    @Command(name = "wsk.team")
    public void teamCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        if (sender.hasPermission("wsk.team.captain"))
            sender.sendMessage(WSK.PREFIX + "/wsk team captain - Setzt jemanden zum Captain");
        if (sender.hasPermission("wsk.team.put"))
            sender.sendMessage(WSK.PREFIX + "/wsk team put - Setzt jemanden in ein Team");
        if (sender.hasPermission("wsk.team.remove"))
            sender.sendMessage(WSK.PREFIX + "/wsk team remove - Entfernt den Spieler aus seinem Team");
    }

    @Command(name = "wsk.team.captain", usage = "/wsk team captain <Spieler>", permission = "wsk.team.captain", description = "Setzt jemanden zum Captain")
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

        if (wskPlayer.isCaptain()) {
            sender.sendMessage(WSK.PREFIX + "§cDieser Spieler ist bereits Captain");
            return;
        }

        if (wsk.getArenaManager().getCurrentArena().getState().isInGame()) {
            sender.sendMessage(WSK.PREFIX + "§cDer Kampf läuft bereits");
            return;
        }

        wskPlayer.getTeam().setCaptain(wskPlayer);
        sender.sendMessage(WSK.PREFIX + "Du hast " + player.getName() + " als Captain gesetzt");
    }

    @Command(name = "wsk.team.remove", usage = "/wsk team remove <Spieler>", permission = "wsk.team.remove", description = "Entfernt den Spieler aus seinem Team")
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

        if (wsk.getArenaManager().getCurrentArena().getState().isInGame()) {
            sender.sendMessage(WSK.PREFIX + "§cDer Kampf läuft bereits");
            return;
        }

        wskPlayer.getTeam().removeMember(wskPlayer);
        sender.sendMessage(WSK.PREFIX + "Du hast " + player.getName() + " aus seinem Team entfernt");
    }

    @Completer(name = "wsk.team.put")
    public List<String> complete(CommandArgs args) {
        List<String> completions = new ArrayList<>();

        if (args.length() > 1) {
            completions.addAll(wsk.getArenaManager().getCurrentArena().getTeams().stream().map(t -> t.getProperties().getName()).collect(Collectors.toList()));
        }
        return completions;
    }

    @Command(name = "wsk.team.put", usage = "/wsk team put <Spieler> <Team>", permission = "wsk.team.put", description = "Setzt jemanden in ein Team")
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

        Team team = wsk.getArenaManager().getCurrentArena().getTeam(args.getArgument(1));
        if (team == null) {
            sender.sendMessage(WSK.PREFIX + "§cDieses Team existiert nicht");
            return;
        }

        if (wsk.getArenaManager().getCurrentArena().getState().isInGame()) {
            sender.sendMessage(WSK.PREFIX + "§cDer Kampf läuft bereits");
            return;
        }

        WSKPlayer wskPlayer = WSKPlayer.getPlayer(player);
        if (wskPlayer.getTeam() != null)
            wskPlayer.getTeam().removeMember(wskPlayer);

        team.addMember(WSKPlayer.getPlayer(player));
        sender.sendMessage(WSK.PREFIX + "Du hast " + player.getName() + " zu " + team.getProperties().getColorCode() + team.getProperties().getName() + " §ehinzugefügt");
    }
}
