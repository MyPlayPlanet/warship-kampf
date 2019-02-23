package net.myplayplanet.wsk.objects;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@RequiredArgsConstructor
@Getter
@Setter
public class TeamRelation {

    private final Team team;
    private final Team enemyTeam;
    private final int pointsPerPercent;
    private final int maxPercent;

}
