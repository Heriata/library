package deveducate.library.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import deveducate.library.aspect.Benchmark;
import deveducate.library.dtos.SubscriptionBookDto;
import deveducate.library.models.BookEntity;
import deveducate.library.models.BookEntryEntity;
import deveducate.library.models.SubscriptionEntity;
import deveducate.library.repositories.BookEntryRepository;
import deveducate.library.repositories.BookRepository;
import deveducate.library.repositories.SubscriptionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessagesProcessingService {

    private final SubscriptionRepository subscriptionRepository;
    private final BookRepository bookRepository;
    private final BookEntryRepository bookEntryRepository;
    private final ObjectMapper objectMapper;
    private final EntityManager entityManager;

    private ArrayList<BookEntryEntity> bookEntries;

    @Benchmark
    @Transactional
    @KafkaListener(topics = "${kafka.topic[0]}", batch = "true")
    public void processMessage(ConsumerRecords<String, String> records) {

        log.info("[+] Processing records: {}", records.count());

        bookEntries = new ArrayList<>();

        records.forEach(x -> {
            SubscriptionBookDto dto;
            try {
                dto = objectMapper.readValue(x.value(), new TypeReference<>() {
                });
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            BookEntity bookEntity = getBookEntity(dto);
            SubscriptionEntity subscriptionEntity = getSubscriptionEntity(dto);
            BookEntryEntity bookEntryEntity = getBookEntryEntity(bookEntity, subscriptionEntity);

            bookEntries.add(bookEntryEntity);
        });

        bookEntryRepository.saveAll(bookEntries);

        log.info("[-] Processed records: {}", records.count());
    }

    private BookEntryEntity getBookEntryEntity(BookEntity bookEntity, SubscriptionEntity subscriptionEntity) {
        BookEntryEntity bookEntryEntity = bookEntryRepository.findByBookBookId(bookEntity.getBookId());
        if (bookEntryEntity == null) {
            bookEntryEntity = BookEntryEntity.builder()
                    .subscription(subscriptionEntity)
                    .book(bookEntity)
                    .build();
            entityManager.persist(bookEntryEntity);
        }
        return bookEntryEntity;
    }

    private SubscriptionEntity getSubscriptionEntity(SubscriptionBookDto dto) {
        SubscriptionEntity subscriptionEntity = subscriptionRepository
                .findByUsername(dto.getUsername());
        if (subscriptionEntity == null) {
            subscriptionEntity = SubscriptionEntity.builder()
                    .userActive(dto.isUserActive())
                    .userFullName(dto.getUserFullName())
                    .username(dto.getUsername())
                    .build();
            entityManager.persist(subscriptionEntity);
        }
        return subscriptionEntity;
    }

    private BookEntity getBookEntity(SubscriptionBookDto dto) {
        BookEntity bookEntity = bookRepository.findByBookNameAndBookAuthor(dto.getBookName(), dto.getBookAuthor());
        if (bookEntity == null) {
            bookEntity = BookEntity.builder()
                    .bookName(dto.getBookName())
                    .bookAuthor(dto.getBookAuthor())
                    .build();
            entityManager.persist(bookEntity);
        }
        return bookEntity;
    }
}

