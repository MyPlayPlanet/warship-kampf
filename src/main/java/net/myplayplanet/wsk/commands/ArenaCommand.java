package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.event.ArenaStateChangeEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@Getter
@AllArgsConstructor
public class ArenaCommand {

    private WSK wsk;

    @Command(name = "wsk.arena.info", permission = "wsk.arena.info", description = "Zeigt Informationen über die momentane Arena")
    public void infoCommand(CommandArgs args) {
        CommandSender sender = args.getSender(CommandSender.class);

        Arena arena = wsk.getArenaManager().getCurrentArena();

        sender.sendMessage(WSK.PREFIX + "===[ §6" + arena.getArenaConfig().getName() + " §e]===");
        sender.sendMessage(WSK.PREFIX + "Status: §c" + arena.getState());

        arena.getTeams().forEach((team) -> {
            sender.sendMessage(WSK.PREFIX + "=== " + team.getProperties().getFullname() + " §e===");
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
}
