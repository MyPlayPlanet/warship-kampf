package net.myplayplanet.wsk.objects.scoreboard;

import net.myplayplanet.wsk.arena.Arena;
import net.myplayplanet.wsk.arena.ArenaState;
import net.myplayplanet.wsk.objects.Team;
import net.myplayplanet.wsk.objects.WSKPlayer;
import org.bukkit.scoreboard.Objective;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class FullInformationSidebar implements ObjectiveWorker {

    // For formatting decimals on scoreboard
    private final DecimalFormat df = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance(Locale.GERMAN));

    @Override
    public void editObjective(Arena arena, Objective objective) {

        int teamCount = arena.getTeams().size();

        String nextState = "";
        if (arena.getState() == ArenaState.SHOOTING)
            nextState = "Zeit bis zum Entern";
        else if (arena.getState() == ArenaState.ENTER)
            nextState = "Zeit bis alle Entern können";
        else if (arena.getState() == ArenaState.ENTER_ALL)
            nextState = "Zeit bis Spielende";

        if (arena.getState() != ArenaState.SPECTATE) {
            objective.getScore(" ").setScore(teamCount * 4 + 3);
            objective.getScore("§6" + nextState).setScore(teamCount * 4 + 2);
            objective.getScore("§8» §c" + toMinutes(arena.getTimer().getSeconds()) + " §7Minuten").setScore(teamCount * 4 + 1);
        }


        final int entries = arena.getTeams().size() * 4;
        StringBuilder empty = new StringBuilder("  ");

        int currentScoreIndex = entries;
        for (int i = 0; i < teamCount; i++) {
            // Decrement, but after execution of this line
            objective.getScore(empty.toString()).setScore(currentScoreIndex--);

            Team team = arena.getTeams().get(i);
            int teamSize = (int) team.getMembers().stream().filter(WSKPlayer::isAlive).count();
            String color = team.getProperties().getColorCode();
            int points = team.calculatePoints();
            if(arena.getState() == ArenaState.SPECTATE)
                points = team.getPoints();

            objective.getScore(color + "Mitglieder §8» §7" + teamSize).setScore(currentScoreIndex--);
            objective.getScore(color + "Beschädigt §8» §8(§7" + df.format(team.getPercentDamage()) + "%§8/§7"
                    + df.format(team.getMaxPercentage()) + "%§8)").setScore(currentScoreIndex--);
            objective.getScore(color + "Punkte  §8» §7" + points).setScore(currentScoreIndex--);
            empty.append(" ");
        }
    }
}
