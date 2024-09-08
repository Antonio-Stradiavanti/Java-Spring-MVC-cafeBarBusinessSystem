package ru.manannikov.bootcupscoffeebar.telegrambot.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageSource messageSource;

    public String load(String messageCode, String languageCode) {
        return messageSource.getMessage(
                messageCode,
                null,
                Locale.of(languageCode)
        );
    }
}
