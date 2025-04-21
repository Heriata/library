package deveducate.library.services;

import deveducate.library.exceptions.EntityNotFoundException;
import deveducate.library.models.SubscriptionEntity;
import deveducate.library.repositories.SubscriptionRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTest {

    private static final String FULL_NAME = "Dukie Beamont";
    private static final String FULL_NAME_EMPTY = "";

    private static final int PAGE_NUMBER = 0;
    private static final int PAGE_SIZE = 10;

    private static PageRequest pageRequest;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @InjectMocks
    private SubscriptionService subscriptionService;

    @BeforeAll
    static void setUp() {
        pageRequest = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
    }

    @Test
    @DisplayName("Should return SubscriptionEntity")
    public void testCreateSubscription() {
        SubscriptionEntity subscriptionEntity = SubscriptionEntity.builder()
                .userFullName(FULL_NAME).build();

        when(subscriptionRepository.findByUserFullName(FULL_NAME, pageRequest)).thenReturn(List.of(subscriptionEntity));

        List<SubscriptionEntity> forName = subscriptionService.getForName(FULL_NAME, pageRequest);

        assertEquals(forName.size(), 1);
        assertEquals(forName.get(0).getUserFullName(), FULL_NAME);
    }

    @Test
    @DisplayName("Should throw when fullName is empty/null")
    public void testSubscriptionNullNameThrow() {
        assertThrows(IllegalArgumentException.class, () -> subscriptionService.getForName(FULL_NAME_EMPTY, pageRequest));
    }

    @Test
    @DisplayName("Should throw when no users with such FullName")
    public void testSubscriptionEmptyNameThrow() {
        when(subscriptionRepository.findByUserFullName(FULL_NAME, pageRequest)).thenReturn(Collections.emptyList());
        assertThrows(EntityNotFoundException.class, () -> subscriptionService.getForName(FULL_NAME, pageRequest));
    }

    @Test
    @DisplayName("Should throw when no such subscription")
    public void testSubscriptionNotFound() {
        when(subscriptionRepository.findByUserFullName(FULL_NAME, pageRequest)).thenReturn(List.of());
        assertThrows(EntityNotFoundException.class, () -> subscriptionService.getForName(FULL_NAME, pageRequest));
    }
}
