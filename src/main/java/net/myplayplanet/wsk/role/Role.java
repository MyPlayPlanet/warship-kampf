package net.myplayplanet.wsk.role;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Role {
    private final String name;
    private final AbstractRole role;
}
