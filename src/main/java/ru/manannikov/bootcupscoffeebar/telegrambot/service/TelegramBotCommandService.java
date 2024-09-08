package ru.manannikov.bootcupscoffeebar.telegrambot.service;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manannikov.bootcupscoffeebar.client.ClientEntity;
import ru.manannikov.bootcupscoffeebar.client.ClientService;
import ru.manannikov.bootcupscoffeebar.telegrambot.TelegramMessageDto;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import static ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramBotKeyboardService.extractButtonCallback;
import static ru.manannikov.bootcupscoffeebar.telegrambot.TelegramBotConstants.*;

/**
 * При использовании этого класса гарантрируем, что клиент зарегистрирован.
 */
@Service
@RequiredArgsConstructor
public class TelegramBotCommandService implements TelegramBotUpdateService {

    private final TelegramClientService telegramClientService;
    private final ClientService clientService;
    private final TelegramBotKeyboardService keyboardService;
    private final MessageService messageService;


    private static final Map<String, Consumer<TelegramMessageDto>> functionCommandMap = new HashMap<>();

    @PostConstruct
    private void init() {
        functionCommandMap.put(
            START_COMMAND,
            (messageDto) -> {

                Long chatId = messageDto.getChatId();

                String clientName = clientService.getByChatId(chatId).get().getName();

                telegramClientService.sendMessage(
                    chatId,
                    String.format(
                        messageService.load("messages.greeting.start.registered", messageDto.getLanguageCode()),
                        clientName
                    )
                );
            }
        );

        functionCommandMap.put(
            PROFILE_COMMAND,
            (messageDto) -> {

            }
        );

    }

    @Override
    public void process(TelegramMessageDto messageDto) {
        String messageText = messageDto.getMessageText();

        Optional.ofNullable(
                messageText.startsWith("/")
                        ? functionCommandMap.get(messageText.split(" ")[0])
                        : functionCommandMap.get(extractButtonCallback(messageText))
        ).ifPresentOrElse(
                consumer -> consumer.accept(messageDto),
                // Если команда не распознана
                () -> {
                    String languageCode = messageDto.getLanguageCode();

                    telegramClientService.sendMessage(
                            messageDto.getChatId(),
                            messageService.load(
                                    "messages.command.not-recognized",
                                    messageDto.getLanguageCode()
                            ),
                            keyboardService.getMainMenuKeyboard(languageCode)
                    );
                }
        );
    }
}
