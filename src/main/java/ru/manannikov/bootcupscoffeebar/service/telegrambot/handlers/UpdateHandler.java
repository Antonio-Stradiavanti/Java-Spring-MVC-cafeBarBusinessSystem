package ru.manannikov.bootcupscoffeebar.service.telegrambot.handlers;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface UpdateHandler {
    SendMessage handle(Update update);
}
