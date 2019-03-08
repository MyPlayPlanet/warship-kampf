package net.myplayplanet.wsk.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.util.Vector;

@Getter
@AllArgsConstructor
@ToString
public class TeamProperties {

    private String name;
    private String colorCode;
    private Location spawn;

    private Vector pos1;
    private Vector pos2;
    private Vector pasteLocation;

    public String getFullname() {
        return colorCode + name;
    }
}
