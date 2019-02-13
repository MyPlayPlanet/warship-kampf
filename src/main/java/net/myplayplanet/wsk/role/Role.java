package net.myplayplanet.wsk.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    CAPTAIN(new CaptainRole()), GUNNER(new GunnerRole());

    final AbstractRole role;
}
