package deveducate.library.services;

import deveducate.library.repositories.projections.ReturnReminderProjection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationService {

    private static final String SUBJECT = "Book return reminder";

    private final MockJavaMailSender mockJavaMailSender;

    public void sendNotificationsToList(List<ReturnReminderProjection> subscriptions) {
        for (ReturnReminderProjection subscription : subscriptions) {
            sendNotification(subscription);
        }
    }

    private void sendNotification(ReturnReminderProjection subscription) {
        String message = String.format("Dear %s, please return \"%s\" by \"%s\" (taken %s)",
                subscription.getUserFullName(),
                subscription.getBookName(),
                subscription.getBookAuthor(),
                subscription.getTakenDate().toString());

        sendNotification(subscription.getUserEmail(), message);
    }

    private void sendNotification(String email, String body) {
        MockJavaMailSender.Message message = mockJavaMailSender.createMimeMessage();

        message.setEmail(email);
        message.setSubject(NotificationService.SUBJECT);
        message.setBody(body);

        mockJavaMailSender.send(message);
    }
}
