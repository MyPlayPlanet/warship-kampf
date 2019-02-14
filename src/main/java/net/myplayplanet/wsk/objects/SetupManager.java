package net.myplayplanet.wsk.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import net.myplayplanet.wsk.arena.ArenaConfig;

import java.util.HashMap;
import java.util.UUID;

@AllArgsConstructor
@Getter
@Setter
public class SetupManager {

    private ArenaConfig config = new ArenaConfig();
    private String name = "default";

    private static HashMap<UUID, SetupManager> instances = new HashMap<>();

    public static SetupManager getInstance(UUID uuid) {
        if (!instances.containsKey(uuid))
            instances.put(uuid, new SetupManager());
        return instances.get(uuid);
    }

    private SetupManager() {
    }
}
