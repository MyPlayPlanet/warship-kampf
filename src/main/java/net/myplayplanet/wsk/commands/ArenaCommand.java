package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.wsk.WSK;
import org.bukkit.command.CommandSender;

@Getter
@AllArgsConstructor
public class ArenaCommand {

    private WSK wsk;

    @Command(name = "wsk.arena.info", permission = "wsk.arena.info", description = "Zeigt Informationen über die momentane Arena")
    public void infoCommand(CommandArgs args) {
        CommandSender cs = args.getSender(CommandSender.class);
    }
}
