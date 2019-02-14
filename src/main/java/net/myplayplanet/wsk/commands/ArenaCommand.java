package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaManager;
import org.bukkit.command.CommandSender;

@Getter
@AllArgsConstructor
public class ArenaCommand {

    private WSK wsk;

    @Command(name = "wsk.arena.info", permission = "wsk.arena.info", description = "Zeigt Informationen über die momentane Arena")
    public void infoCommand(CommandArgs args) {
        CommandSender cs = args.getSender(CommandSender.class);

        Arena arena = ArenaManager.getInstance().getCurrentArena();

        cs.sendMessage(WSK.PREFIX + "===[ §6" + arena.getArenaConfig().getName() + "§e]===");
        cs.sendMessage(WSK.PREFIX + "Status: §c" + arena.getState());

        arena.getTeams().forEach((team) -> {
            cs.sendMessage(WSK.PREFIX + "= " + team.getProperties().getFullname() + " §e=");
            if(team.getMembers().size() > 0) {
                cs.sendMessage(WSK.PREFIX + "Kapitän: §c" + team.getCaptain().getPlayer().getName());
                cs.sendMessage(WSK.PREFIX + "Teamgröße: §c" + team.getMembers().size());
            }
        });
    }
}
