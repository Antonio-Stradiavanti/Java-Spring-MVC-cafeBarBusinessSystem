package ru.manannikov.cafeBarBusinessSystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.manannikov.cafeBarBusinessSystem.enums.Role;

import java.time.LocalDate;
import java.util.Collection;

@Entity
@Table(name="users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity implements UserDetails {
    @Id @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    /**
     * В качестве имени пользователя будем использовать email.
     */
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    private String lastname;
    private String firstname;
    private String patronym;

    @Column(name="phone_number", nullable = false, unique = true)
    private String phoneNumber;

    private LocalDate birthday;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role = Role.EMPLOYEE;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    /**
     * @return true если пароль пользователя действителен, то есть его срок действия еще не истек.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    /**
     * @return true если пользователь может пройти аутентификацию
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}
