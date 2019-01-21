package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.wsk.WSK;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

@AllArgsConstructor
public class SetupCommand {

    private final WSK wsk;

    @Command(name = "wsk.setup", permission = "wsk.setup")
    public void mainCommand(CommandArgs args) {
        //TODO implement help
    }

    @Command(name = "wsk.setup.save", permission = "wsk.setup", inGameOnly = true, description = "Speichert die Arenaconfig", usage = "/wsk setup save")
    public void saveCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        manager.getConfig().save("plugins/WSK/arenas/" + manager.getName() + ".json");
        player.sendMessage(WSK.PREFIX + "Arenaconfig gespeichert");
    }

    @Command(name = "wsk.setup.name", permission = "wsk.setup", inGameOnly = true, description = "Setzt den Name der Arena", usage = "/wsk setup name <Name>")
    public void nameCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        if (args.getArgumentCount() == 0) {
            player.sendMessage(WSK.PREFIX + "§c/wsk setup name <Name>");
            return;
        }
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        manager.setName(args.getArgument(0));
        player.sendMessage(WSK.PREFIX + "Name gesetzt");
    }

    @Command(name = "wsk.setup.spawn", permission = "wsk.setup", inGameOnly = true, description = "Setzt den Spawn der Arena", usage = "/wsk setup spawn")
    public void spawnCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        Location location = player.getLocation();
        manager.getConfig().setSpawn(location);
        player.sendMessage(WSK.PREFIX + "Spawn gesetzt");
    }

    @Command(name = "wsk.setup.spectatorspawn", permission = "wsk.setup", inGameOnly = true, description = "Setzt den Spectatorspawn der Arena", usage = "/wsk setup spectatorspawn")
    public void spectatorSpawnCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        Location location = player.getLocation();
        manager.getConfig().setSpectatorSpawn(location);
        player.sendMessage(WSK.PREFIX + "Spawn gesetzt");
    }

    @Command(name = "wsk.setup.waterheight", permission = "wsk.setup", inGameOnly = true, description = "Setzt die Höhe des Wasserniveaus Arena", usage = "/wsk setup waterheight <Höhe>")
    public void waterHeightCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        if (args.getArgumentCount() == 0) {
            player.sendMessage(WSK.PREFIX + "§c/wsk setup waterheight <Höhe>");
            return;
        }
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        try {
            manager.getConfig().setWaterHeight(Integer.parseInt(args.getArgument(0)));
            player.sendMessage(WSK.PREFIX + "Wasserniveau gesetzt");
        } catch (NumberFormatException e) {
            player.sendMessage(WSK.PREFIX + "§c'" + args.getArgument(0) + "' ist keine Zahl");
        }
    }

    @Command(name = "wsk.setup.pos1", permission = "wsk.setup", inGameOnly = true, description = "Setzt die erste Position der Arena", usage = "/wsk setup pos1")
    public void pos1Command(CommandArgs args) {
        Player player = args.getSender(Player.class);

        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        Location location = player.getLocation();
        manager.getConfig().setPos1(new Vector(location.getX(), location.getY(), location.getZ()));
        player.sendMessage(WSK.PREFIX + "Erste Position gesetzt");
    }


    @Command(name = "wsk.setup.pos2", permission = "wsk.setup", inGameOnly = true, description = "Setzt die zweite Position der Arena", usage = "/wsk setup pos2")
    public void pos2Command(CommandArgs args) {
        Player player = args.getSender(Player.class);

        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        Location location = player.getLocation();
        manager.getConfig().setPos2(new Vector(location.getX(), location.getY(), location.getZ()));
        player.sendMessage(WSK.PREFIX + "Zweite Position gesetzt");
    }

    @Command(name = "wsk.setup.world", permission = "wsk.setup", inGameOnly = true, description = "Setzt den Weltnamen der Arena", usage = "/wsk setup world <Name>")
    public void worldCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        if (args.getArgumentCount() == 0) {
            player.sendMessage(WSK.PREFIX + "§c/wsk setup world <Name>");
            return;
        }
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        manager.getConfig().setWorld(args.getArgument(0));
        player.sendMessage(WSK.PREFIX + "Welt gesetzt");
    }
}