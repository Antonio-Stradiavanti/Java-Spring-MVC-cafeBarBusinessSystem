package ru.manannikov.bootcupscoffeebar.telegrambot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static ru.manannikov.bootcupscoffeebar.telegrambot.TelegramBotConstants.*;
import static ru.manannikov.bootcupscoffeebar.utils.ObjectUtils.doTry;

@Service
@RequiredArgsConstructor
public class TelegramBotKeyboardService {

    private final ObjectMapper objectMapper;
    private final MessageService messageService;

    public static String extractButtonCallback(String buttonText) {
        return Optional.ofNullable(buttonText)
                .map(text -> {
                    int spaceIndex = text.indexOf(BUTTON_CALLBACK_DIVIDER);
                    return spaceIndex != -1 ? text.substring(0, spaceIndex + 1) : "";
                })
                .orElse("");
    }

    public ReplyKeyboard getMainMenuKeyboard(String languageCode) {

        KeyboardRow row1 = new KeyboardRow(
                messageService.load(
                        "messages.menu.bonus-card",
                        languageCode
                )
        );

        KeyboardRow row2 = new KeyboardRow(
                messageService.load(
                        "messages.menu.profile",
                        languageCode
                )
        );

        return ReplyKeyboardMarkup.builder()
                .keyboard(List.of(row1, row2))
                .resizeKeyboard(true)
                .isPersistent(true)
                .oneTimeKeyboard(false)
                .build();
    }

    public InlineKeyboardMarkup getAvailableLanguages(String languageCode) {
        List<InlineKeyboardRow> keyboard = new ArrayList<>(
                AVAILABLE_LANGUAGES.entrySet().stream()
                        .filter(entry -> !entry.getKey().equals(languageCode))
                        .map(
                                entry -> InlineKeyboardButton.builder()
                                        .text(String.format("%s %s", entry.getValue().emoji(), entry.getValue().name()))
                                        .callbackData(
                                                // При возникновении указанного проверяемого исключения в ходе вызова метода writeValueAsString дальнейшая работа программы не имеет смысла
                                                doTry(() -> objectMapper
                                                        .writeValueAsString(
                                                                Map.ofEntries(
                                                                        Map.entry(CALLBACK_OPERATION, PROFILE_CHANGE_LANGUAGE),
                                                                        Map.entry(CALLBACK_LANGUAGE_CODE, entry.getKey())
                                                                )
                                                        )
                                                )
                                        )
                                        .build()
                        )
                        .map(InlineKeyboardRow::new)
                        .toList()
        );
        return new InlineKeyboardMarkup(keyboard);
    }

    public InlineKeyboardMarkup getProfileKeyboard(String languageCode) {

        InlineKeyboardButton changeLanguage = InlineKeyboardButton.builder()
                .text(messageService.load("messages.profile.change-language", languageCode))
                .callbackData(
                        doTry(
                                () ->
                                        objectMapper.writeValueAsString(
                                                Map.ofEntries(
                                                        Map.entry(CALLBACK_OPERATION, PROFILE_VIEW_LANGUAGE),
                                                        Map.entry(CALLBACK_LANGUAGE_CODE, languageCode)
                                                )
                                        )
                        )
                )
                .build();

        List<InlineKeyboardRow> keyboard = List.of(
                new InlineKeyboardRow(changeLanguage)
        );

        return new InlineKeyboardMarkup(keyboard);
    }

}
