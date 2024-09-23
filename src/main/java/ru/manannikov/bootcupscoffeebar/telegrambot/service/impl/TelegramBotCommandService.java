package ru.manannikov.bootcupscoffeebar.telegrambot.service.impl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.manannikov.bootcupscoffeebar.bonuscard.BonusCardDto;
import ru.manannikov.bootcupscoffeebar.client.ClientDto;
import ru.manannikov.bootcupscoffeebar.client.ClientService;
import ru.manannikov.bootcupscoffeebar.telegrambot.TelegramMessageDto;
import ru.manannikov.bootcupscoffeebar.telegrambot.service.MessageService;
import ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramBotKeyboardService;
import ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramBotUpdateService;
import ru.manannikov.bootcupscoffeebar.telegrambot.service.TelegramClientService;

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
            messageService.load("messages.greeting.start.registered", messageDto.getLangCode()),
            clientName
          )
        );
      }
    );

    functionCommandMap.put(
      PROFILE_COMMAND,
      (messageDto) -> {
        Long chatId = messageDto.getChatId(); String langCode = messageDto.getLangCode();

        telegramClientService.deleteMessage(
          chatId,
          messageDto.getMessageId()
        );

        ClientDto client = messageDto.getClient();
        telegramClientService.sendMessage(
          chatId,
          String.format(
            messageService.load(
              "messages.profile",
              langCode
            ),
            client == null ? messageService.load("messages.default.name", langCode) : client.name()
          ),
          keyboardService.getProfileKeyboard(langCode)
        );
      }
    );

    functionCommandMap.put(
      BONUS_CARD_COMMAND,
      (messageDto) -> {
        Long chatId = messageDto.getChatId(); String langCode = messageDto.getLangCode();

        telegramClientService.deleteMessage(
          chatId,
          messageDto.getMessageId()
        );

        ClientDto client = messageDto.getClient();
        telegramClientService.sendMessage(
          chatId,
          client == null
            ?
              messageService.load("messages.bonus-card.unavailable", langCode)
            :
              String.format(
                messageService.load("messages.bonus-card.info", langCode),
                client.bonusCard().amount(),
                client.bonusCard().discountPercent()
              )
        );
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
      // Если такая команда есть, то выполняем ее
      consumer -> consumer.accept(messageDto),
      // Иначе выводим локализованное сообщение об ошибке
      () -> {
        String languageCode = messageDto.getLangCode();

        telegramClientService.sendMessage(
          messageDto.getChatId(),
          messageService.load(
            "messages.command.not-recognized",
            messageDto.getLangCode()
          ),
          keyboardService.getMainMenuKeyboard(languageCode)
        );
      }
    );
  }
}
