package deveducate.library.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import deveducate.library.dtos.SubscriptionBookDto;
import deveducate.library.models.BookEntity;
import deveducate.library.models.SubscriptionEntity;
import deveducate.library.repositories.BookEntryRepository;
import deveducate.library.repositories.BookRepository;
import deveducate.library.repositories.SubscriptionRepository;
import deveducate.library.utils.TestUtils;
import jakarta.persistence.EntityManager;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class MessageProcessingServiceTest {

    private static final String TOPIC_LIBRARY = "library";

    @InjectMocks
    private MessagesProcessingService messageProcessingService;

    @Mock
    private SubscriptionRepository subscriptionRepository;
    @Mock
    private BookRepository bookRepository;
    @Mock
    private BookEntryRepository bookEntryRepository;
    @Mock
    private EntityManager entityManager;
    @Spy
    private ObjectMapper objectMapper;

    @Captor
    private ArgumentCaptor<Object> objectArgumentCaptor;

    @SneakyThrows
    @ParameterizedTest
    @DisplayName("Should save 2 records")
    @MethodSource("deveducate.library.utils.ArgumentProvider#provideSubscriptionBookDtoList")
    public void testEntitiesParsedAndSaved(List<SubscriptionBookDto> list) {
        long distinctBooksCount = 2;

        SubscriptionBookDto dto0 = list.get(0);
        when(bookRepository.findByBookNameAndBookAuthor(dto0.getBookName(), dto0.getBookAuthor()))
                .thenReturn(null).thenReturn(TestUtils.toBookEntity(dto0, 0L));

        SubscriptionBookDto dto1 = list.get(1);
        when(bookRepository.findByBookNameAndBookAuthor(dto1.getBookName(), dto1.getBookAuthor()))
                .thenReturn(null).thenReturn(TestUtils.toBookEntity(dto1, 1L));


        messageProcessingService.processMessage(TestUtils.getConsumerRecords(list, TOPIC_LIBRARY));

        verify(bookRepository).findByBookNameAndBookAuthor(dto0.getBookName(), dto0.getBookAuthor());
        verify(bookRepository).findByBookNameAndBookAuthor(dto1.getBookName(), dto1.getBookAuthor());

        verify(entityManager, atMost(list.size() * 3)).persist(objectArgumentCaptor.capture());

        long count = objectArgumentCaptor.getAllValues().stream().filter(x -> x instanceof BookEntity).count();
        assertEquals(distinctBooksCount, count);
    }

    @SneakyThrows
    @ParameterizedTest
    @DisplayName("Should save only 1 book item")
    @MethodSource("deveducate.library.utils.ArgumentProvider#provideSubscriptionBookDtoListWithBookDuplicates")
    public void testDuplicateBookEntitiesIgnored(List<SubscriptionBookDto> list) {

        SubscriptionBookDto dto0 = list.get(0);
        when(bookRepository.findByBookNameAndBookAuthor(dto0.getBookName(), dto0.getBookAuthor()))
                .thenReturn(null).thenReturn(TestUtils.toBookEntity(dto0, 1L));

        SubscriptionBookDto dto1 = list.get(1);
        when(bookRepository.findByBookNameAndBookAuthor(dto1.getBookName(), dto1.getBookAuthor()))
                .thenReturn(null).thenReturn(TestUtils.toBookEntity(dto1, 1L));

        messageProcessingService.processMessage(TestUtils.getConsumerRecords(list, TOPIC_LIBRARY));

        verify(bookRepository, times(2))
                .findByBookNameAndBookAuthor(dto0.getBookName(), dto0.getBookAuthor());
        verify(entityManager, atMost(list.size() * 3)).persist(objectArgumentCaptor.capture());

        long distinctBooksCount = 1;
        long count = objectArgumentCaptor.getAllValues().stream().filter(x -> x instanceof BookEntity).count();
        assertEquals(distinctBooksCount, count);
    }

    @SneakyThrows
    @ParameterizedTest
    @DisplayName("Should save only one Subscription")
    @MethodSource("deveducate.library.utils.ArgumentProvider#provideSubscriptionBookDtoListWithUsernameDuplicates")
    public void testDuplicateUsernameIgnored(List<SubscriptionBookDto> list) {

        SubscriptionBookDto dto0 = list.get(0);
        when(subscriptionRepository.findByUsername(dto0.getUsername()))
                .thenReturn(null)
                .thenReturn(TestUtils.toSubscriptionEntity(dto0, 0L));

        messageProcessingService.processMessage(TestUtils.getConsumerRecords(list, TOPIC_LIBRARY));

        verify(subscriptionRepository, times(2)).findByUsername(dto0.getUsername());
        verify(entityManager, atMost(list.size() * 3)).persist(objectArgumentCaptor.capture());

        long distinctSubscriptionsCount = 1;
        long count = objectArgumentCaptor.getAllValues().stream().filter(x -> x instanceof SubscriptionEntity).count();
        assertEquals(distinctSubscriptionsCount, count);
    }
}
