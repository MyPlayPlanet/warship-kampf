package net.myplayplanet.wsk.role;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Role {

    CAPTAIN(new CaptainRole()),
    GUNNER(new GunnerRole()),
    SPECIALFORCE(new SpecialforceRole());

    final AbstractRole role;
}
