package net.myplayplanet.wsk.arena;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ArenaState {


    IDLE, SETUP, PRERUNNING(true), SHOOTING(true), ENTER(true), ENTER_ALL(true), SPECTATE(true);

    boolean inGame;

    ArenaState() {
        this(false);
    }
}
