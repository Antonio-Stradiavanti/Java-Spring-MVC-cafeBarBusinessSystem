package ru.manannikov.bootcupscoffeebar.telegrambot.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.DeleteMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import static ru.manannikov.bootcupscoffeebar.telegrambot.TelegramBotConstants.PARSE_MODE;

@Service
@Slf4j
@RequiredArgsConstructor
public class TelegramClientService {
    private final TelegramClient client;

    public void sendMessage(
            Long chatId,
            String messageText
    ) {
        sendMessage(chatId, messageText, null);
    }

    public void sendMessage(
        Long chatId,
        String messageText,
        ReplyKeyboard replyKeyboard
    ) {
        try {
            SendMessage messageToSend = SendMessage.builder()
                    .chatId(chatId)
                    .text(messageText)
                    .parseMode(PARSE_MODE)
                    .replyMarkup(replyKeyboard)
                    .build();
            client.execute(messageToSend);
        } catch (TelegramApiException | NoSuchMessageException ex) {
            log.error("Send message error: ", ex);
        }
    }

    public void deleteMessage(
            Long chatId,
            Integer messageId
    ) {
        try {
            DeleteMessage messageToDelete = DeleteMessage.builder()
                    .chatId(chatId)
                    .messageId(messageId)
                    .build();
            client.execute(messageToDelete);
        } catch (TelegramApiException ex) {
            log.error("Delete message error: ", ex);
        }
    }
}
