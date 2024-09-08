package ru.manannikov.bootcupscoffeebar.client;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
    Optional<ClientEntity> findByChatId(Long chatId);
}
