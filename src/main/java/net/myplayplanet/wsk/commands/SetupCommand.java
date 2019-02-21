package net.myplayplanet.wsk.commands;

import lombok.AllArgsConstructor;
import net.myplayplanet.commandframework.CommandArgs;
import net.myplayplanet.commandframework.api.Command;
import net.myplayplanet.wsk.WSK;
import net.myplayplanet.wsk.arena.ArenaConfig;
import net.myplayplanet.wsk.objects.SetupManager;
import net.myplayplanet.wsk.util.Logger;
import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;

@AllArgsConstructor
public class SetupCommand {

    private final WSK wsk;

    @Command(name = "wsk.setup", permission = "wsk.setup", inGameOnly = true)
    public void mainCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        player.sendMessage(WSK.PREFIX + "/wsk setup load - Lädt eine Arenaconfig");
        player.sendMessage(WSK.PREFIX + "/wsk setup save - Speichert eine Arenaconfig");
        player.sendMessage(WSK.PREFIX + "/wsk setup name - Benennt eine momentan bearbeitet Arena");
        player.sendMessage(WSK.PREFIX + "/wsk setup spawn - Setzt den Spawn der Arena");
        player.sendMessage(WSK.PREFIX + "/wsk setup spectatorspawn - Setzt den Spawn der Arena für Zuschauer");
        player.sendMessage(WSK.PREFIX + "/wsk setup waterheight - Setzt die Wasserhöhe");
        player.sendMessage(WSK.PREFIX + "/wsk setup pos1 - Setzt die erste Position der Arena");
        player.sendMessage(WSK.PREFIX + "/wsk setup pos2 - Setzt die zweite Position der Arena");
        player.sendMessage(WSK.PREFIX + "/wsk setup world - Benutzt die benannte Welt als Arenawelt");
    }

    @Command(name = "wsk.setup.load", permission = "wsk.setup", inGameOnly = true, description = "Lädt eine Arenaconfig", usage = "/wsk setup load <Name>")
    public void loadCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        if (args.getArgumentCount() == 0) {
            player.sendMessage(WSK.PREFIX + "§c/wsk setup load <Name>");
            return;
        }
        String configName = args.getArgument(0);
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());

        manager.setName(configName);
        manager.setConfig(ArenaConfig.loadFromFile(new File(wsk.getDataFolder(), "arenas/" + configName + ".json")));

        player.sendMessage(WSK.PREFIX + "§aArenaconfig '" + configName + "' geladen");
    }

    @Command(name = "wsk.setup.save", permission = "wsk.setup", inGameOnly = true, description = "Speichert die Arenaconfig", usage = "/wsk setup save")
    public void saveCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        manager.getConfig().save("plugins/WSK/arenas/" + manager.getName() + ".json");
        player.sendMessage(WSK.PREFIX + "§aArenaconfig gespeichert");
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
        manager.getConfig().setName(args.getArgument(0));
        player.sendMessage(WSK.PREFIX + "Name gesetzt");
    }

    @Command(name = "wsk.setup.spawn", permission = "wsk.setup", inGameOnly = true, description = "Setzt den Spawn der Arena", usage = "/wsk setup spawn")
    public void spawnCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        Location location = player.getLocation();
        // Set world to null because of serialization
        location.setWorld(null);

        manager.getConfig().setSpawn(location);
        player.sendMessage(WSK.PREFIX + "Spawn gesetzt");
    }

    @Command(name = "wsk.setup.spectatorspawn", permission = "wsk.setup", inGameOnly = true, description = "Setzt den Spectatorspawn der Arena", usage = "/wsk setup spectatorspawn")
    public void spectatorSpawnCommand(CommandArgs args) {
        Player player = args.getSender(Player.class);
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        Location location = player.getLocation();
        // Set world to null because of serialization
        location.setWorld(null);

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
        String worldName = args.getArgument(0);
        World world = Bukkit.getWorld(worldName);
        if (world == null) {
            player.sendMessage(WSK.PREFIX + "§cKeine Welt mit dem Namen '" + worldName + "' gefunden");
            return;
        }
        SetupManager manager = SetupManager.getInstance(player.getUniqueId());
        manager.getConfig().setWorld(worldName);
        File file = new File(Bukkit.getWorldContainer(), worldName);
        try {
            File worldDir = new File(wsk.getDataFolder(), "arenas/" + worldName);
            FileUtils.copyDirectory(file, worldDir);
            File uid = new File(worldDir, "uid.dat");
            if (uid.isFile())
                FileUtils.deleteQuietly(uid);
            player.sendMessage(WSK.PREFIX + "Welt gesetzt");
        } catch (IOException e) {
            Logger.ERROR.log("Could not copy world at setup");
            e.printStackTrace();
        }
    }
}