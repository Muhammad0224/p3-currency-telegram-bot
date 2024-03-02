package com.skillbox.cryptobot.service;

import com.skillbox.cryptobot.entity.Subscriber;
import com.skillbox.cryptobot.repo.SubscriberRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriberService {
    private final SubscriberRepo subscriberRepo;

    public void saveSubscriber(Long chatId) {
        Subscriber subscriber = new Subscriber();
        subscriber.setChatId(chatId);

        subscriberRepo.save(subscriber);
    }

    public void subscribe(Long chatId, Double subscriptionAmount) {
        Optional<Subscriber> optionalSubscriber = subscriberRepo.findByChatId(chatId);
        if (optionalSubscriber.isPresent()) {
            Subscriber subscriber = optionalSubscriber.get();
            subscriber.setSubscriberAmount(subscriptionAmount);

            subscriberRepo.save(subscriber);
        }
    }

    public Double getSubscription(Long chatId) {
        Optional<Subscriber> optionalSubscriber = subscriberRepo.findByChatId(chatId);
        return optionalSubscriber.map(Subscriber::getSubscriberAmount).orElse(null);
    }

    public void unsubscribe(Long chatId) {
        Optional<Subscriber> optionalSubscriber = subscriberRepo.findByChatId(chatId);
        if (optionalSubscriber.isPresent()) {
            Subscriber subscriber = optionalSubscriber.get();
            subscriber.setSubscriberAmount(null);

            subscriberRepo.save(subscriber);
        }
    }

    public List<Subscriber> getSubscribers(Double amount, LocalDateTime localDateTime) {
        return subscriberRepo.getNotificationSubscribers(amount, localDateTime);
    }

    public Subscriber getSubscriber(Long chatId) {
        Optional<Subscriber> optionalSubscriber = subscriberRepo.findByChatId(chatId);
        return optionalSubscriber.orElse(null);
    }

    public void save(List<Subscriber> subscribers) {
        subscriberRepo.saveAll(subscribers);
    }

    public void save(Subscriber subscriber) {
        subscriberRepo.save(subscriber);
    }
}
