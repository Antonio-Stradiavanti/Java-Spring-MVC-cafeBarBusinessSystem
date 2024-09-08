package ru.manannikov.bootcupscoffeebar.service.telegrambot.handlers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import ru.manannikov.bootcupscoffeebar.client.ClientEntity;
import ru.manannikov.bootcupscoffeebar.client.ClientService;

import static ru.manannikov.bootcupscoffeebar.telegrambot.TelegramBotConstants.*;
import static ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState.ASK_NAME;
import static ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramClientService.messageBuilder;

/**
 * Отвечает за отправку напоминания через указанное количество секунд
 */
@Service
@RequiredArgsConstructor
public class CallbackHandler implements UpdateHandler {
    private final ClientService clientService;

    @Override
    public SendMessage handle(Update update) {
        CallbackQuery query = update.getCallbackQuery();
        // Возвращает callback data кнопки
        String callbackData = query.getData();
        Long chatId = query.getChatId();

        SendMessage sendMessage = null;
        String messageText = "";

        if (callbackData.equals(REGISTRATION_YES_BUTTON)) {
            ClientEntity client = ClientEntity.builder()
                    .chatId(chatId)
                    .registrationState(ASK_NAME)
            .build();

            // Сохраняем клиента и каскадируем эту операцию на бонусную карту
            clientService.create(client);

           messageText = NAME_PROMPT;
        } else if (callbackData.equals(REGISTRATION_NO_BUTTON)) {
            messageText = COME_BACK_PROMPT;
        }

        return messageBuilder(chatId, messageText).build();
    }
}
