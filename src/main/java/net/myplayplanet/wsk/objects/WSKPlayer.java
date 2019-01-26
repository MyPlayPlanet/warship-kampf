package net.myplayplanet.wsk.objects;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@EqualsAndHashCode(exclude = "team")
public class WSKPlayer {
    private Team team;
    private UUID uuid;
}
