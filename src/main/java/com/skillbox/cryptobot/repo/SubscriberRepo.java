package com.skillbox.cryptobot.repo;

import com.skillbox.cryptobot.entity.Subscriber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SubscriberRepo extends JpaRepository<Subscriber, UUID> {

    @Query("select s from Subscriber s where s.subscriberAmount >= ?1 and (s.lastNotification < ?2 or s.lastNotification is null)")
    List<Subscriber> getNotificationSubscribers(Double subscriberAmount, LocalDateTime lastNotification);

    Optional<Subscriber> findByChatId(Long chatId);
}
