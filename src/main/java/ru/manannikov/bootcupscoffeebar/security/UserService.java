package ru.manannikov.bootcupscoffeebar.security;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.manannikov.bootcupscoffeebar.exceptions.EntityNotFoundException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findByUsername(username).orElseThrow(() ->
            new UsernameNotFoundException(
                String.format("Пользователь с именем %s не найден", username)
        ));
    }

    public List<UserEntity> findAll() {
        return repository.findAll();
    }

    public UserEntity findById(Long id) {
        return repository.findById(id).orElseThrow(() ->
            new EntityNotFoundException(
                String.format("Пользователь с идентификатором %d не найден", id)
            )
        );
    }

    public UserEntity save(UserEntity user) {
        // Пароль надо зашифровать перед добавлением записи в таблицу
        user.setPassword(
            passwordEncoder.encode(user.getPassword())
        );
        return repository.save(user);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    @PostConstruct
    public void initTable() {
        if (repository.count() == 0) {
            repository.save(
                new UserEntity(
                    null,
                    "senioravanti@vk.com",
                    passwordEncoder.encode("12345"),

                    "Мананников",
                    "Антон",
                    "Олегович",

                    "+7 999 679 34-21",
                    LocalDate.parse("14.08.2004", DateTimeFormatter.ofPattern("dd.MM.yyyy")),

                    Role.CEO
                )
            );
        }
    }
}