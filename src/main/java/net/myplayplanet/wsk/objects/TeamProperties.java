package net.myplayplanet.wsk.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.util.Vector;

import java.util.Map;

@Getter
@AllArgsConstructor
@ToString
public class TeamProperties {

    private String name;
    private String colorCode;
    private Map<String, Object> spawnMap;

    private Vector pos1;
    private Vector pos2;
    private Vector pasteLocation;

    @Setter
    private transient World loadedWorld;

    public String getFullname() {
        return colorCode + name;
    }

    public Location getSpawn() {
        Location location = Location.deserialize(spawnMap);
        location.setWorld(loadedWorld);
        return location;
    }
}
