package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.commandframework.api.Completer;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.ArenaStateChangeEvent;
import net.myplayplanet.wsk.objects.Team;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class ArenaCommand {

    private WSK wsk;

    @Command(name = "wsk.arena.info", permission = "wsk.arena.info", description = "Zeigt Informationen über die momentane Arena")
    public void infoCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        Arena arena = wsk.getArenaManager().getCurrentArena();

        sender.sendMessage(WSK.PREFIX + "===[ §6" + arena.getArenaConfig().getName() + " §7]===");
        sender.sendMessage(WSK.PREFIX + "Status: §c" + arena.getState());

        arena.getTeams().forEach((team) -> {
            sender.sendMessage(WSK.PREFIX + "=== " + team.getProperties().getFullname() + " §7===");
            if (team.getMembers().size() > 0) {
                sender.sendMessage(WSK.PREFIX + "Kapitän: §c" + team.getCaptain().getPlayer().getName());
                sender.sendMessage(WSK.PREFIX + "Teamgröße: §c" + team.getMembers().size());
            }
        });
    }

    @Command(name = "wsk.arena.start", permission = "wsk.arena.start", description = "Startet den Kampf")
    public void startCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        Arena arena = wsk.getArenaManager().getCurrentArena();

        if (arena.getState() != ArenaState.SETUP) {
            sender.sendMessage(WSK.PREFIX + "§cDer Kampf kann gerade nicht gestartet werden");
            return;
        }

        ArenaStateChangeEvent event = new ArenaStateChangeEvent(ArenaState.SETUP, ArenaState.PRERUNNING, arena);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Command(name = "wsk.arena.stop", permission = "wsk.arena.stop", description = "Stoppt den Kampf mit den berechneten Punkten")
    public void stopCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        Arena arena = wsk.getArenaManager().getCurrentArena();

        if (!arena.getState().isInGame()) {
            sender.sendMessage(WSK.PREFIX + "§cDer Kampf kann gerade nicht gestoppt werden");
            return;
        }

        ArenaStateChangeEvent event = new ArenaStateChangeEvent(arena.getState(), ArenaState.SPECTATE, arena);
        Bukkit.getPluginManager().callEvent(event);
    }

    @Command(name = "wsk.arena.draw", permission = "wsk.arena.draw", description = "Erzwingt ein Unentschieden")
    public void drawCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        Arena arena = wsk.getArenaManager().getCurrentArena();

        if (!arena.getState().isInGame()) {
            sender.sendMessage(WSK.PREFIX + "§cDer Kampf kann gerade nicht gestoppt werden");
            return;
        }

        if (arena.getTimer() != null && !arena.getTimer().isCancelled())
            arena.getTimer().cancel();
        arena.setTimer(null);

        arena.stop(null);
    }

    @Command(name = "wsk.arena.win", permission = "wsk.arena.win", usage = "/wsk arena win <Team>", description = "Lässt ein Team gewinnen")
    public void winCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        Arena arena = wsk.getArenaManager().getCurrentArena();

        if (args.length() < 1) {
            sender.sendMessage(WSK.PREFIX + "§c/wsk arena win <Team>");
            return;
        }

        if (!arena.getState().isInGame()) {
            sender.sendMessage(WSK.PREFIX + "§cDer Kampf kann gerade nicht gestoppt werden");
            return;
        }

        Team team = arena.getTeam(args.getArgument(0));
        if (team == null) {
            sender.sendMessage(WSK.PREFIX + "§cDieses Team existiert nicht");
            return;
        }

        if (arena.getTimer() != null && !arena.getTimer().isCancelled())
            arena.getTimer().cancel();
        arena.setTimer(null);

        arena.stop(team);
    }


    @Completer(name = "wsk.arena.win")
    public List<String> complete(CommandArgs args) {
        List<String> completions = new ArrayList<>();

        completions.addAll(wsk.getArenaManager().getCurrentArena().getTeams().stream().map(t -> t.getProperties().getName()).collect(Collectors.toList()));

        return completions;
    }
}
