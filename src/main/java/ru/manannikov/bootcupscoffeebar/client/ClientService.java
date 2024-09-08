package ru.manannikov.bootcupscoffeebar.client;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manannikov.bootcupscoffeebar.bonuscard.BonusCardEntity;
import ru.manannikov.bootcupscoffeebar.bonuscard.BonusCardService;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientService {
    private final ClientRepository repository;
    private final BonusCardService service;

    public Optional<ClientEntity> getByChatId(Long chatId) {
        return repository.findByChatId(chatId);
    }

    public void save(ClientEntity client) {
        repository.save(client);
    }

    /**
     * У переданного экземпляра client еще нет идентификатора
     */
    @Transactional
    public void create(ClientEntity client) {
        BonusCardEntity bonusCard = new BonusCardEntity();
        bonusCard.setAmount(BigDecimal.valueOf(0.0));
        bonusCard.setClient(client);

        // Сохранит и бонусную карту и клиента
        service.save(bonusCard);
    }
}
