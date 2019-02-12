package net.myplayplanet.wsk;

import lombok.Getter;
import net.myplayplanet.commandframework.CommandFramework;
import net.myplayplanet.wsk.arena.ArenaManager;
import net.myplayplanet.wsk.commands.SetupCommand;
import net.myplayplanet.wsk.commands.TeamCommand;
import net.myplayplanet.wsk.commands.WSKCommand;
import net.myplayplanet.wsk.listener.ArenaListener;
import net.myplayplanet.wsk.listener.PlayerListener;
import net.myplayplanet.wsk.objects.ScoreboardManager;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public class WSK extends JavaPlugin {

    @Getter
    private CommandFramework framework;
    private static WSK instance;
    public static final String PREFIX = "§8[§6WSK§8] §e";

    @Override
    public void onEnable() {
        instance = this;

        // Load config
        Config.load();

        framework = new CommandFramework(this);
        framework.registerCommands(new WSKCommand(this));
        framework.registerCommands(new SetupCommand(this));
        if (!Config.isSetup())
            framework.registerCommands(new TeamCommand(this));

        // Create arenas folder
        File file = new File(getDataFolder(), "arenas");
        file.mkdirs();

        // Register listeners
        PluginManager pm = Bukkit.getPluginManager();
        pm.registerEvents(new PlayerListener(), this);
        pm.registerEvents(new ArenaListener(), this);

        for (Player player : Bukkit.getOnlinePlayers()) {
            WSKPlayer.add(player);
        }

        // Initialize ArenaManager with WSK instance
        new ArenaManager(this);

        ScoreboardManager.getInstance();
    }

    public static WSK getInstance() {
        return instance;
    }

}
