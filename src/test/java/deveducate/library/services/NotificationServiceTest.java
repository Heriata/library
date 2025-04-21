package deveducate.library.services;

import deveducate.library.repositories.projections.ReturnReminderProjection;
import deveducate.library.utils.TestUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTest {

    @InjectMocks
    private NotificationService service;

    @Mock
    private MockJavaMailSender mailSender;

    @Test
    public void testSendEmail() {
        List<ReturnReminderProjection> list = TestUtils.getListOfProjections();

        when(mailSender.createMimeMessage()).thenReturn(TestUtils.getMessage());
        service.sendNotificationsToList(list);

        verify(mailSender, times(list.size())).send(any(MockJavaMailSender.Message.class));
    }
}
