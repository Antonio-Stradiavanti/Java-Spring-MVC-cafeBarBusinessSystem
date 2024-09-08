package ru.manannikov.bootcupscoffeebar.bonuscard;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BonusCardRepository extends JpaRepository<BonusCardEntity, Long> {
    Optional<BonusCardEntity> findByClientId(Long id);
}
