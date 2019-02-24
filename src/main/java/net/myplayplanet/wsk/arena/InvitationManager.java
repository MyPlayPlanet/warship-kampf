package net.myplayplanet.wsk.arena;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.myplayplanet.wsk.objects.Team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Getter
public class InvitationManager {

    private final Arena arena;
    private HashMap<UUID, List<Invite>> invites = new HashMap<>();

    public boolean invite(UUID uuid, Team team) {
        // Create new entry if not present
        if (!invites.containsKey(uuid))
            invites.put(uuid, new ArrayList<>());

        List<Invite> invs = invites.get(uuid);
        if (invs.stream().filter(invite -> invite.getTeam() == team).count() > 0) return false;
        Invite invite = new Invite(team, uuid, this);
        invs.add(invite);
        invite.runTimer();
        return true;
    }

    public boolean isInvited(UUID uuid, Team team) {
        if (!invites.containsKey(uuid))
            return false;
        if (invites.get(uuid).stream().filter(invite -> invite.getTeam() == team).count() > 0) return true;
        return false;
    }

    public boolean removeInvite(UUID uuid, Team team) {
        if (!isInvited(uuid, team))
            return false;
        Invite invite = invites.get(uuid).stream().filter(inv -> inv.getTeam() == team).collect(Collectors.toList()).get(0);
        return invites.get(uuid).remove(invite);
    }
}
