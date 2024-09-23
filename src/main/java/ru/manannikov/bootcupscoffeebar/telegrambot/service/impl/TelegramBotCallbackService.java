package ru.manannikov.bootcupscoffeebar.telegrambot.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manannikov.bootcupscoffeebar.telegrambot.TelegramMessageDto;
import ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramBotUpdateService;

@Service
@RequiredArgsConstructor
public class TelegramBotCallbackService implements TelegramBotUpdateService {



    @Override
    public void process(TelegramMessageDto messageDto) {

    }
}
