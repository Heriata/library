package deveducate.library.services;

import deveducate.library.repositories.SubscriptionRepository;
import deveducate.library.repositories.projections.ReturnReminderProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookReturnReminderService {

    private final SubscriptionRepository subscriptionRepository;
    private final NotificationService notificationService;

    // every 30 seconds
    // use "0 0 0 * * *" for everyday check
    @Transactional(readOnly = true)
    @Scheduled(cron = "*/30 * * * * *")
    public void bookReturnReminder() {

        List<ReturnReminderProjection> subscriptions = subscriptionRepository.findAllWhereTakenDateIsOlder();
        if (!subscriptions.isEmpty()) {
            log.info("Send reminders, to: {}", subscriptions.size());
            notificationService.sendNotificationsToList(subscriptions);
        }
    }
}
