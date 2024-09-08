package ru.manannikov.bootcupscoffeebar.bonuscard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manannikov.bootcupscoffeebar.exceptions.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class BonusCardService {

    private final BonusCardRepository repository;

    public BonusCardEntity getById(Long id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("В базе данных отсутствует бонусная карта с указанным идентификатором"));
    }

    public BonusCardEntity getByClientId(Long id) {
        return repository.findByClientId(id).orElseThrow(() -> new EntityNotFoundException("Клиент с указанным идентификатором не зарегистрирован"));
    }

    public void save(BonusCardEntity bonusCard) {
        repository.save(bonusCard);
    }

}
