package ru.manannikov.bootcupscoffeebar.service.telegrambot.handlers.registration;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState;
import ru.manannikov.bootcupscoffeebar.client.ClientEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState.*;
import static ru.manannikov.bootcupscoffeebar.telegrambot.TelegramBotConstants.*;
import static ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramClientService.messageBuilder;

/**
 * Отвечает за последовательную регистрацию клиента
 */
@Service
public class ClientRegistrationHandler {
    final Map<RegistrationState, RegistrationHandler> registrationHandlers = new HashMap<>();

    public ClientRegistrationHandler(
        InitRegistrationHandler initRegistrationHandler,
        AskNameRegistrationHandler askNameRegistrationHandler,
        AskBirthDateRegistrationHandler askBirthDateRegistrationHandler,
        AskEmailRegistrationHandler askEmailRegistrationHandler
    ) {
        registrationHandlers.put(RegistrationState.ASK_REGISTRATION, initRegistrationHandler);
        registrationHandlers.put(ASK_NAME, askNameRegistrationHandler);
        registrationHandlers.put(ASK_BIRTHDAY, askBirthDateRegistrationHandler);
        registrationHandlers.put(ASK_EMAIL, askEmailRegistrationHandler);
    }

    public SendMessage register(Optional<ClientEntity> clientOptional, Long chatId, String message) {
        // Сводим полезную нагрузку объекта Optional к типу RegistrationState
        RegistrationState registrationState = clientOptional.map(ClientEntity::getRegistrationState).orElse(RegistrationState.ASK_REGISTRATION);

        if (message.equals(BONUS_COMMAND)) {
            return messageBuilder(chatId, BONUS_CARD_ARE_NOT_AVAILABLE).build();
        }
        // Используем полученный объект, чтобы перейти к текущей стадии регистрации
        ClientEntity client = clientOptional.orElse(null);
        // Определим этап регистрации
        return registrationHandlers.get(registrationState).handle(client, chatId, message);
    }
}
