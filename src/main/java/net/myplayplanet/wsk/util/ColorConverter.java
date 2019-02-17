package net.myplayplanet.wsk.util;

import org.bukkit.Material;

public class ColorConverter {

    public static Material getConcreteFromColorCode(String s) {
        if (s.startsWith("§")) {
            s = s.substring(1);
        }
        switch (s) {
            case "f":
                return Material.WHITE_CONCRETE;
            case "6":
                return Material.ORANGE_CONCRETE;
            case "d":
                return Material.PINK_CONCRETE;
            case "b":
                return Material.CYAN_CONCRETE;
            case "e":
                return Material.YELLOW_CONCRETE;
            case "a":
                return Material.GREEN_CONCRETE;
            case "8":
                return Material.GRAY_CONCRETE;
            case "7":
                return Material.LIGHT_GRAY_CONCRETE;
            case "3":
                return Material.LIGHT_BLUE_CONCRETE;
            case "5":
                return Material.MAGENTA_CONCRETE;
            case "1":
                return Material.BLUE_CONCRETE;
            case "9":
                return Material.BLUE_CONCRETE;
            case "2":
                return Material.GREEN_CONCRETE;
            case "c":
            case "4":
                return Material.RED_CONCRETE;
            case "0":
                return Material.WHITE_CONCRETE;
            default:
                throw new IllegalArgumentException();
        }
    }
}
