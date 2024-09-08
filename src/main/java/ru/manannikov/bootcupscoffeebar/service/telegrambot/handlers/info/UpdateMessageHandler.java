package ru.manannikov.bootcupscoffeebar.service.telegrambot.handlers.info;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.manannikov.bootcupscoffeebar.bonuscard.BonusCardEntity;
import ru.manannikov.bootcupscoffeebar.client.ClientService;

import static ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramClientService.messageBuilder;
import static ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramBotKeyboardService.buildKeyboard;
/**
 * Отправляет сообщение зарегистрированному клиенту
 */
@Service
@RequiredArgsConstructor
public class UpdateMessageHandler {

    private final ClientService clientService;

    /**
     * Клиент гарантированно зарегистрирован
     */
    public SendMessage handle(Long chatId, String command) {
        return switch (command) {
            case "/start" -> messageBuilder(
                    chatId, START_MESSAGE
                )
                .replyMarkup(buildKeyboard())
                .build();

            case "/bonus", "Бонусная карта" -> {
                BonusCardEntity bonusCard = clientService.getByChatId(chatId).get().getBonusCard();

                String message = String.format("У вас %s баллов", bonusCard.getAmount());

                yield messageBuilder(chatId, message)
                    .replyMarkup(buildKeyboard())
                    .build();
            }

            default -> messageBuilder(chatId, NOT_RECOGNIZED).build();

       };
    }

}
