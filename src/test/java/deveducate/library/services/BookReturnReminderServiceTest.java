package deveducate.library.services;

import deveducate.library.repositories.SubscriptionRepository;
import deveducate.library.repositories.projections.ReturnReminderProjection;
import deveducate.library.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookReturnReminderServiceTest {

    @InjectMocks
    private BookReturnReminderService service;
    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private NotificationService notificationService;

    @Test
    public void testNotification() {
        when(subscriptionRepository.findAllWhereTakenDateIsOlder()).thenReturn(Collections.emptyList());
        service.bookReturnReminder();
        verify(notificationService, never()).sendNotificationsToList(anyList());
    }

    @Test
    public void testSubscription() {
        List<ReturnReminderProjection> list = TestUtils.getListOfProjections();

        when(subscriptionRepository.findAllWhereTakenDateIsOlder()).thenReturn(list);

        service.bookReturnReminder();

        verify(notificationService).sendNotificationsToList(list);
    }
}
