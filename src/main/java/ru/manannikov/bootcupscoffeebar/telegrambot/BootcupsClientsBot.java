package ru.manannikov.bootcupscoffeebar.telegrambot;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.objects.CallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.message.Message;

import ru.manannikov.bootcupscoffeebar.client.ClientDto;
import ru.manannikov.bootcupscoffeebar.client.ClientEntity;
import ru.manannikov.bootcupscoffeebar.client.ClientService;
import ru.manannikov.bootcupscoffeebar.telegrambot.service.impl.TelegramBotCallbackService;
import ru.manannikov.bootcupscoffeebar.telegrambot.service.impl.TelegramBotCommandService;
import ru.manannikov.bootcupscoffeebar.telegrambot.service.impl.TelegramBotRegistrationService;

import java.util.Map;
import java.util.Optional;

import static ru.manannikov.bootcupscoffeebar.telegrambot.RegistrationState.REGISTERED;
import static ru.manannikov.bootcupscoffeebar.telegrambot.TelegramBotConstants.*;
import static ru.manannikov.bootcupscoffeebar.utils.ObjectUtils.doTry;

@Service @Slf4j @RequiredArgsConstructor
public class BootcupsClientsBot implements LongPollingSingleThreadUpdateConsumer {

  private final ClientService clientService;
  private final TelegramBotCommandService commandService;
  private final TelegramBotRegistrationService registrationService;
  private final TelegramBotCallbackService callbackService;
  private final ObjectMapper objectMapper;

  @Override public void consume(Update update) {

    switch (update) {
      case Update u when u.hasMessage() && u.getMessage().hasText() -> {
        onMessage(update.getMessage());
      }

      case Update u when u.hasCallbackQuery() -> {
        onCallbackQuery(update.getCallbackQuery());
      }

      default -> throw new IllegalStateException("Unexpected value: " + update);
    }
  }

  private void onMessage(Message message) {
    Long chatId = message.getChatId();

    TelegramMessageDto messageDto = TelegramMessageDto.builder().chatId(chatId)

      .messageText(message.getText()).messageId(message.getMessageId())

      .build()
      ;

    Optional<ClientEntity> clientEntityOptional = clientService.getByChatId(chatId);

    boolean isRegistered = clientEntityOptional
      .map(clientEntity -> clientEntity.getRegistrationState().equals(REGISTERED))
      .orElse(false);

    String languageCode;
    if (isRegistered) {
      ClientEntity clientEntity = clientEntityOptional.get();
      languageCode = clientEntity.getLangCode();

      messageDto.setLangCode(languageCode != null && !languageCode.isEmpty() && AVAILABLE_LANGUAGES.containsKey(
        languageCode) ? languageCode : DEFAULT_LANGUAGE_CODE);

      messageDto.setClient(ClientDto.fromEntity(clientEntity));

      commandService.process(messageDto);
    } else {
      languageCode = message.getFrom().getLanguageCode();

      messageDto.setLangCode(AVAILABLE_LANGUAGES.containsKey(languageCode) ? languageCode : DEFAULT_LANGUAGE_CODE);

      registrationService.process(messageDto);
    }
  }

  private void onCallbackQuery(CallbackQuery query) {
    Map<String, String> callbackData = doTry(() -> objectMapper.readValue(query.getData(), CALLBACK_DATA_TYPE));

    TelegramMessageDto telegramMessageDto = TelegramMessageDto
      .builder()
      .chatId(query.getMessage().getChatId())
      .messageId(query.getMessage().getMessageId())
      .langCode(callbackData.get(CALLBACK_LANGUAGE_CODE))
      .build()
      ;
  }
}
