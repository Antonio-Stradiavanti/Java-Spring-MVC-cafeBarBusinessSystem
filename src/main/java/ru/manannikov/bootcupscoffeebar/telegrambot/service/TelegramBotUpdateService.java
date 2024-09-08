package ru.manannikov.bootcupscoffeebar.telegrambot.service;

import ru.manannikov.bootcupscoffeebar.telegrambot.TelegramMessageDto;

public interface TelegramBotUpdateService {
    void process(TelegramMessageDto messageDto);
}
