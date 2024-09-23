package ru.manannikov.bootcupscoffeebar.telegrambot;

import lombok.Builder;
import lombok.Data;
import ru.manannikov.bootcupscoffeebar.client.ClientDto;

import static ru.manannikov.bootcupscoffeebar.telegrambot.TelegramBotConstants.DEFAULT_LANGUAGE_CODE;

@Data
@Builder
public class TelegramMessageDto {
  private Long chatId;
  private Integer messageId;
  private String messageText;
  private String langCode = DEFAULT_LANGUAGE_CODE;
  private ClientDto client;
}
