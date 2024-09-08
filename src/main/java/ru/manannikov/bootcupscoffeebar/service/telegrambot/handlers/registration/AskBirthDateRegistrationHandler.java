package ru.manannikov.bootcupscoffeebar.service.telegrambot.handlers.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.manannikov.bootcupscoffeebar.client.ClientEntity;
import ru.manannikov.bootcupscoffeebar.client.ClientService;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.FormatStyle;
import java.util.Locale;

import static ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState.ASK_EMAIL;
import static ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramClientService.messageBuilder;

@Service
@RequiredArgsConstructor
public class AskBirthDateRegistrationHandler implements RegistrationHandler {

    private final ClientService service;

    @Override
    public SendMessage handle(ClientEntity client, Long chatId, String message) {

        String messageToSend = registrationProgress(client.getRegistrationState());

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.of("ru", "RU"));
            LocalDate birthday = LocalDate.parse(message, formatter);

            client.setBirthday(birthday);
            messageToSend = messageToSend.concat(EMAIL_PROMPT);

            client.setRegistrationState(ASK_EMAIL);

            service.save(client);
        } catch (DateTimeParseException ex) {
            messageToSend = messageToSend.concat(INVALID_BIRTHDAY);
        }

        return messageBuilder(chatId, messageToSend).build();
    }
}