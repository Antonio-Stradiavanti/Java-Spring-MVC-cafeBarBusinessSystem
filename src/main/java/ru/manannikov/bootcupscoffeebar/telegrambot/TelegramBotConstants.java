package ru.manannikov.bootcupscoffeebar.telegrambot;

import com.fasterxml.jackson.core.type.TypeReference;

import java.util.HashMap;
import java.util.Map;

public class TelegramBotConstants {

    public static final String PARSE_MODE = "markdown";

    public static final char BUTTON_CALLBACK_DIVIDER = '\u00A0';

    public static final String START_COMMAND = "/start";
    public static final String PROFILE_COMMAND = "\ud83d\udc64" + BUTTON_CALLBACK_DIVIDER;
    public static final String BONUS_CARD_COMMAND = "\ud83d\udcb3" + BUTTON_CALLBACK_DIVIDER;

    public static final String BONUS_CARD_ARE_NOT_AVAILABLE = "";


    public static final String DEFAULT_LANGUAGE_CODE = "ru";
    public static final Map<String, LanguageDto> AVAILABLE_LANGUAGES = Map.ofEntries(
            Map.entry("en", new LanguageDto("\ud83c\uddfa\ud83c\uddf8", "English")),
            Map.entry("ru", new LanguageDto("\ud83c\uddf7\ud83c\uddfa", "Русский"))
    );

    public static final TypeReference<HashMap<String, String>> CALLBACK_DATA_TYPE =
            new TypeReference<>() {};

    public static final String CALLBACK_LANGUAGE_CODE = "LC";
    public static final String CALLBACK_OPERATION = "O";
    public static final String PROFILE_CHANGE_LANGUAGE = "PCL";
    public static final String PROFILE_VIEW_LANGUAGE = "PVL";
    public static final String REGISTRATION_YES_BUTTON = "RYB";
    public static final String REGISTRATION_NO_BUTTON = "RNB";
}
