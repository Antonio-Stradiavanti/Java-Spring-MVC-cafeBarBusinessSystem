package ru.manannikov.cafeBarBusinessSystem.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static ru.manannikov.cafeBarBusinessSystem.enums.Permission.*;

@RequiredArgsConstructor
@Getter
public enum Role {
    CLIENT("Гость заведения", Set.of(
        PRODUCT_READ
    )),
    EMPLOYEE("Сотрудник", Set.of(
        PRODUCT_READ,
        PRODUCT_UPDATE,

        USER_READ
    )),
    CEO("Руководитель заведения", Set.of(
        PRODUCT_CREATE,
        PRODUCT_READ,
        PRODUCT_UPDATE,
        PRODUCT_DELETE,

        USER_CREATE,
        USER_READ,
        USER_UPDATE,
        USER_DELETE
    ))
    ;

    private final String humanReadableName;
    private final Set<Permission> authorities;

    @Override
    public String toString() {
        return "ROLE_" + super.name();
    }

    public Set<SimpleGrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> simpleGrantedAuthorities = authorities.stream()
            .map(permission ->
                new SimpleGrantedAuthority(permission.getAuthority())
                )
            .collect(Collectors.toSet())
        ;
        simpleGrantedAuthorities.add(
            new SimpleGrantedAuthority(this.toString())
        );
        return simpleGrantedAuthorities;
    }
}
