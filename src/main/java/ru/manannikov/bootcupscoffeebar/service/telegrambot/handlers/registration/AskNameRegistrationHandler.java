package ru.manannikov.bootcupscoffeebar.service.telegrambot.handlers.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.manannikov.bootcupscoffeebar.client.ClientEntity;
import ru.manannikov.bootcupscoffeebar.client.ClientService;

import static ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState.ASK_BIRTHDAY;
import static ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramClientService.messageBuilder;

@Service
@RequiredArgsConstructor
public class AskNameRegistrationHandler implements RegistrationHandler {

    private final ClientService service;

    @Override
    public SendMessage handle(ClientEntity client, Long chatId, String message) {

        client.setName(message);
        String messageToSend = registrationProgress(client.getRegistrationState()) +
                BIRTHDAY_PROMPT;

        client.setRegistrationState(ASK_BIRTHDAY);

        // Обновляем состояние клиента в контексте БД, эта операция не должна каскадироваться.
        service.save(client);

        return messageBuilder(chatId, messageToSend).build();
    }
}
