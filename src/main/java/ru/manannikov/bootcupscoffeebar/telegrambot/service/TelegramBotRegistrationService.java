package ru.manannikov.bootcupscoffeebar.telegrambot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manannikov.bootcupscoffeebar.telegrambot.TelegramMessageDto;

@Service
@RequiredArgsConstructor
public class TelegramBotRegistrationService implements TelegramBotUpdateService {

    @Override
    public void process(TelegramMessageDto messageDto) {

    }
}
