package ru.manannikov.bootcupscoffeebar.service.telegrambot.handlers.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.manannikov.bootcupscoffeebar.client.ClientEntity;
import ru.manannikov.bootcupscoffeebar.client.ClientService;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState.REGISTERED;
import static ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramClientService.messageBuilder;

@Service
@RequiredArgsConstructor
public class AskEmailRegistrationHandler implements RegistrationHandler {

    private final ClientService service;

    @Override
    public SendMessage handle(ClientEntity client, Long chatId, String message) {
        String messageToSend = "";

        if (isValidEmail(message)) {
            client.setEmail(message);

            messageToSend = SUCCESSFULLY_REGISTERED;

            client.setRegistrationState(REGISTERED);

            service.save(client);
        } else {
           messageToSend = registrationProgress(client.getRegistrationState()) +
                INVALID_EMAIL
           ;
        }

        return messageBuilder(chatId, messageToSend).build();
    }

    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        // Сопоставляем паттерн с данной строкой
        return matcher.matches();
    }
}
