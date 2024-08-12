package ru.manannikov.cafeBarBusinessSystem.enums;

import org.springframework.security.core.GrantedAuthority;

public enum Permission implements GrantedAuthority {
    USER_CREATE,
    USER_READ,
    USER_UPDATE,
    USER_DELETE,

    PRODUCT_CREATE,
    PRODUCT_READ,
    PRODUCT_UPDATE,
    PRODUCT_DELETE
    ;

    @Override
    public String getAuthority() {
        return name();
    }
}
