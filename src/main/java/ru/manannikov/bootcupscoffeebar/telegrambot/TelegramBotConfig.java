package ru.manannikov.bootcupscoffeebar.telegrambot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;

@Configuration
public class TelegramBotConfig {
    private final String token;

    public TelegramBotConfig(
        @Value("${telegram.bot.token}") String token
    ) {
        this.token = token;
    }

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsApplication(final BootcupsClientsBot bootcupsClientsBot)
            throws TelegramApiException
    {
        TelegramBotsLongPollingApplication bot = new TelegramBotsLongPollingApplication();

        bot.registerBot(token, bootcupsClientsBot);

        return bot;
    }

    @Bean
    public TelegramClient telegramClient() {
        return new OkHttpTelegramClient(token);
    }
}
