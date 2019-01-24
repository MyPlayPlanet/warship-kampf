package net.myplayplanet.wsk.objects;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.arena.Arena;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RequiredArgsConstructor
@Getter
public class Team implements Iterable<WSKPlayer>{

    private List<WSKPlayer> members = new ArrayList<>();
    private final TeamProperties properties;
    private final Arena arena;

    public void addMember(WSKPlayer player) {

    }


    public Iterator<WSKPlayer> iterator() {
        return members.iterator();
    }
}
