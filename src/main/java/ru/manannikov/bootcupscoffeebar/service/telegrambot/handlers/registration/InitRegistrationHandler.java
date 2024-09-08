package ru.manannikov.bootcupscoffeebar.service.telegrambot.handlers.registration;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import ru.manannikov.bootcupscoffeebar.client.ClientEntity;
import ru.manannikov.bootcupscoffeebar.client.ClientService;

import static ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramClientService.messageBuilder;
import static ru.manannikov.bootcupscoffeebar.telegrambot.TelegramBotConstants.*;

@Service
@RequiredArgsConstructor
public class InitRegistrationHandler implements RegistrationHandler {

    private final ClientService service;

    @Override
    public SendMessage handle(ClientEntity client, Long chatId, String message) {
//        client.setChatId(chatId);
//        String messageToSend = registrationProgress(client.getRegistrationState()) +
//                "Давайте познакомимся, как вас зовут ?";
//
//        client.setRegistrationState(ASK_NAME);
//
//        service.create(client);
        return messageBuilder(chatId, REGISTRATION_PROMPT)
            .replyMarkup(InlineKeyboardMarkup.builder()
                    .keyboardRow(new InlineKeyboardRow(REGISTRATION_YES_BUTTON))
                    .keyboardRow(new InlineKeyboardRow(REGISTRATION_NO_BUTTON))
            .build()
        )
        .build();

    }
}
