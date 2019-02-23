package net.myplayplanet.wsk.util;

import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.objects.Team;

import java.util.HashMap;

/**
 * Little util class to start calculations when all teams
 * initial blocks have been counted
 */
public class InitialCalculator {

    private HashMap<Team, Boolean> teams = new HashMap<>();
    private final Arena arena;

    public InitialCalculator(Arena arena) {
        this.arena = arena;
        arena.getTeams().forEach(team -> teams.put(team, false));
    }

    public void readyTeam(Team team) {
        teams.put(team, true);
        checkTeamsReady();
    }

    private void checkTeamsReady() {
        for (boolean ready : teams.values()) {
            if (!ready)
                return;
        }
        teams.keySet().forEach(Team::setCalculations);
        arena.getScoreboardManager().getSidebar().updateScoreboard();
    }
}
