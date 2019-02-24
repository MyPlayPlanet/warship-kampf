package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.commandframework.api.Completer;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
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
        sender.sendMessage(WSK.PREFIX + "Du hast " + player.getName() + " zu " + team.getProperties().getColorCode() + team.getProperties().getName() + " §7hinzugefügt");
    }

    @Command(name = "wsk.team.invite", usage = "/wsk team invite <Spieler>", inGameOnly = true, description = "Lädt jemanden in dein Team ein")
    public void inviteCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);

        if (args.length() < 1) {
            player.sendMessage(WSK.PREFIX + "§c/wsk team invite <Spieler>");
            return;
        }

        Player toInvite = Bukkit.getPlayerExact(args.getArgument(0));
        if (toInvite == null || !toInvite.isOnline()) {
            player.sendMessage(WSK.PREFIX + "§cDieser Spieler ist nicht online");
            return;
        }

        Arena arena = wsk.getArenaManager().getCurrentArena();
        WSKPlayer wskPlayer = WSKPlayer.getPlayer(player);
        if (!wskPlayer.isCaptain()) {
            player.sendMessage(WSK.PREFIX + "§cDu bist kein Kapitän");
            return;
        }

        if (arena.getState() != ArenaState.SETUP) {
            player.sendMessage(WSK.PREFIX + "§cDu kannst das gerade nicht tun");
            return;
        }

        Team team = wskPlayer.getTeam();
        if (arena.getInvitationManager().isInvited(toInvite.getUniqueId(), team)) {
            player.sendMessage(WSK.PREFIX + "§cDieser Spieler wurde bereits eingeladen");
            return;
        }

        if (WSKPlayer.getPlayer(toInvite).getTeam() != null) {
            player.sendMessage(WSK.PREFIX + "§cDieser Spieler ist bereits in einem Team");
            return;
        }

        arena.getInvitationManager().invite(toInvite.getUniqueId(), team);
        toInvite.sendMessage(WSK.PREFIX + "Du wurdest zu " + team.getProperties().getFullname() + " §7eingeladen");

        TextComponent accept = new TextComponent("Annehmen");
        accept.setColor(ChatColor.GREEN);
        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wsk team accept " + team.getProperties().getName()));

        TextComponent decline = new TextComponent("Ablehnen");
        decline.setColor(ChatColor.RED);
        decline.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/wsk team decline " + team.getProperties().getName()));


        toInvite.sendMessage(combine(combine(TextComponent.fromLegacyText(WSK.PREFIX),
                inBrackets(accept)), inBrackets(decline)));

        player.sendMessage(WSK.PREFIX + "Du hast " + toInvite.getName() + " eingeladen");
    }

    @Command(name = "wsk.team.accept", usage = "/wsk team accept <Team>", inGameOnly = true, description = "Nimmt eine Einladung an")
    public void acceptCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);

        if (args.length() < 1) {
            player.sendMessage(WSK.PREFIX + "§c//wsk team accept <Team>");
            return;
        }
        Arena arena = wsk.getArenaManager().getCurrentArena();
        WSKPlayer wskPlayer = WSKPlayer.getPlayer(player);
        Team team = arena.getTeam(args.getArgument(0));

        if (team == null) {
            player.sendMessage(WSK.PREFIX + "§cUnbekanntes Team");
            return;
        }

        if (!arena.getInvitationManager().isInvited(player.getUniqueId(), team)) {
            player.sendMessage(WSK.PREFIX + "§cDu hast keine Einladung von diesem Team");
            return;
        }

        if (arena.getState() != ArenaState.SETUP) {
            player.sendMessage(WSK.PREFIX + "§cDu kannst das gerade nicht tun");
            return;
        }

        arena.getInvitationManager().removeInvite(player.getUniqueId(), team);

        if (team.getCaptain() != null) {
            team.getCaptain().getPlayer().sendMessage(WSK.PREFIX + player.getName() + " hat deine Einladung angenommen");
        }
        player.sendMessage(WSK.PREFIX + "Du hast die Einladung zu " + team.getProperties().getFullname() + " §7angenommen");

        team.addMember(wskPlayer);
    }

    @Command(name = "wsk.team.leave", usage = "/wsk team leave", inGameOnly = true, description = "Verlasse dein Team")
    public void leaveCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);

        Arena arena = wsk.getArenaManager().getCurrentArena();

        if (arena.getState() != ArenaState.SETUP) {
            player.sendMessage(WSK.PREFIX + "§cDu kannst das gerade nicht tun");
            return;
        }

        WSKPlayer wskPlayer = WSKPlayer.getPlayer(player);
        if (wskPlayer.getTeam() == null) {
            player.sendMessage(WSK.PREFIX + "§cDu bist gerade in keinem Team");
            return;
        }
        player.sendMessage(WSK.PREFIX + "Du hast dein Team verlassen");
        wskPlayer.getTeam().removeMember(wskPlayer);
    }


    @Command(name = "wsk.team.decline", usage = "/wsk team decline <Team>", inGameOnly = true, description = "Lehnt eine Einladung ab")
    public void declineCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);

        if (args.length() < 1) {
            player.sendMessage(WSK.PREFIX + "§c//wsk team decline <Team>");
            return;
        }
        Arena arena = wsk.getArenaManager().getCurrentArena();
        Team team = arena.getTeam(args.getArgument(0));

        if (team == null) {
            player.sendMessage(WSK.PREFIX + "§cUnbekanntes Team");
            return;
        }

        if (!arena.getInvitationManager().isInvited(player.getUniqueId(), team)) {
            player.sendMessage(WSK.PREFIX + "§cDu hast keine Einladung von diesem Team");
            return;
        }

        if (arena.getState() != ArenaState.SETUP) {
            player.sendMessage(WSK.PREFIX + "§cDu kannst das gerade nicht tun");
            return;
        }

        arena.getInvitationManager().removeInvite(player.getUniqueId(), team);

        if (team.getCaptain() != null) {
            team.getCaptain().getPlayer().sendMessage(WSK.PREFIX + player.getName() + " hat deine Einladung abgelehnt");
        }
        player.sendMessage(WSK.PREFIX + "Du hast die Einladung von " + team.getProperties().getFullname() + " §7abgelehnt");
    }


    private BaseComponent[] inBrackets(TextComponent component) {
        BaseComponent[] firstBracket = TextComponent.fromLegacyText("§8[");
        BaseComponent[] lastBracket = TextComponent.fromLegacyText("§8] ");
        return combine(combine(firstBracket, component), lastBracket);
    }

    private BaseComponent[] combine(BaseComponent[] components, BaseComponent singleComponent) {
        BaseComponent[] newComponents = new BaseComponent[components.length + 1];
        System.arraycopy(components, 0, newComponents, 0, components.length);
        newComponents[components.length] = singleComponent;
        return newComponents;
    }


    private BaseComponent[] combine(BaseComponent singleComponent, BaseComponent[] components) {
        BaseComponent[] newComponents = new BaseComponent[components.length + 1];
        System.arraycopy(components, 0, newComponents, 1, components.length);
        newComponents[0] = singleComponent;
        return newComponents;
    }

    private BaseComponent[] combine(BaseComponent[] components, BaseComponent[] otherComponents) {
        BaseComponent[] newComponents = new BaseComponent[components.length + otherComponents.length];
        System.arraycopy(components, 0, newComponents, 0, components.length);
        System.arraycopy(otherComponents, 0, newComponents, components.length, otherComponents.length);
        return newComponents;
    }
}
