package com.skillbox.cryptobot.task;

import com.skillbox.cryptobot.bot.CryptoBot;
import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.service.CryptoCurrencyService;
import com.skillbox.cryptobot.service.SubscriberService;
import com.skillbox.cryptobot.utils.TextUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class SubscribeTask {

    private final CryptoCurrencyService cryptoCurrencyService;
    private final SubscriberService subscriberService;
    private final CryptoBot bot;

    @Value("${config.min-time}")
    private Integer minTime;

    @Scheduled(cron = "${config.refresh-time}")
    public void subscribeTask() {
        try {
            double bitcoinPrice = cryptoCurrencyService.getBitcoinPrice();

            LocalDateTime localDateTime = LocalDateTime.now().minusMinutes(minTime);

            List<Subscriber> subscribers = subscriberService.getSubscribers(bitcoinPrice, localDateTime);
            subscribers.forEach(subscriber -> {
                SendMessage message = new SendMessage();
                message.setChatId(subscriber.getChatId());
                message.setText("Пора покупать, стоимость биткоина " + TextUtil.toString(bitcoinPrice));
                bot.sendMessage(message);

                subscriber.setLastNotification(LocalDateTime.now());
            });
            subscriberService.save(subscribers);
        } catch (IOException e) {
            log.error("Error occurred", e);
        }

    }
}
