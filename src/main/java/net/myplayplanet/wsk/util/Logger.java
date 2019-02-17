package net.myplayplanet.wsk.util;

import lombok.AllArgsConstructor;
import org.bukkit.Bukkit;

import java.util.logging.Level;

@AllArgsConstructor
public enum Logger {

    BOOT("[WSK|Boot]", Level.INFO),
    NORMAL("[WSK]", Level.INFO),
    WARN("[WSK|Warn]", Level.WARNING),
    ERROR("[WSK|Error]", Level.SEVERE);

    private String prefix;
    private Level level;

    public void log(String msg) {
        Bukkit.getLogger().log(level, prefix + " " + msg);
    }
}
