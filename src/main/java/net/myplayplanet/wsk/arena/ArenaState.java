package net.myplayplanet.wsk.arena;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ArenaState {


    IDLE, SETUP, PRERUNNING, SHOOTING, ENTER, ENTER_ALL, SPECTATE;

    boolean inGame;

    ArenaState() {
        this(false);
    }
}
