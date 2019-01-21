package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.wsk.WSK;
import org.bukkit.entity.Player;

@AllArgsConstructor
public class WSKCommand {

    private final WSK wsk;

    @Command(name = "wsk", inGameOnly = true)
    public void mainCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        player.sendMessage(WSK.PREFIX + "WSK v" + WSK.getInstance().getDescription().getVersion() + " by Butzlabben");
        player.sendMessage(WSK.PREFIX + "Hilfe: §a/wsk help");
    }
}
