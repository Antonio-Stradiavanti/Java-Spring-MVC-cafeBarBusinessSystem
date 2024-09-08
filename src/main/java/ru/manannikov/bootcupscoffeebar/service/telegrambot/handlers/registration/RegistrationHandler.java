package ru.manannikov.bootcupscoffeebar.service.telegrambot.handlers.registration;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState;
import ru.manannikov.bootcupscoffeebar.client.ClientEntity;

import static ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState.REGISTERED;

public interface RegistrationHandler {
    String REGISTRATION_PREFIX = "Регистрация. Шаг %d из %d\n\n";

    default String registrationProgress(RegistrationState registrationState) {
        return String.format(REGISTRATION_PREFIX, registrationState.ordinal(), REGISTERED.ordinal());
    }

    SendMessage handle(ClientEntity client, Long chatId, String message);
}
