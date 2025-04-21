package deveducate.library.utils;

import deveducate.library.dtos.SubscriptionBookDto;
import deveducate.library.models.BookEntity;
import deveducate.library.models.BookEntryEntity;
import deveducate.library.models.SubscriptionEntity;
import deveducate.library.repositories.projections.ReturnReminderProjection;
import deveducate.library.services.MockJavaMailSender;
import lombok.SneakyThrows;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.record.TimestampType;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class TestUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static SubscriptionBookDto getSubscriptionBookDto(String username,
                                                             String userFullName,
                                                             String bookName,
                                                             String bookAuthor) {
        return SubscriptionBookDto.builder()
                .username(username)
                .userFullName(userFullName)
                .bookName(bookName)
                .bookAuthor(bookAuthor)
                .build();
    }

    @SneakyThrows
    public static ConsumerRecords<String, String> getConsumerRecords(List<SubscriptionBookDto> dtoList, String topic) {
        Map<TopicPartition, List<ConsumerRecord<String, String>>> records = new LinkedHashMap<>();
        ArrayList<ConsumerRecord<String, String>> objects = new ArrayList<>();
        records.put(new TopicPartition(topic, 0), new ArrayList<>());

        for (SubscriptionBookDto subscriptionBookDto : dtoList) {
            String dto = objectMapper.writeValueAsString(subscriptionBookDto);
            ConsumerRecord<String, String> record = new ConsumerRecord<>(topic, 0, 0, 0L,
                    TimestampType.CREATE_TIME, 0L, subscriptionBookDto.getUsername().length(),
                    dto.getBytes().length, subscriptionBookDto.getUsername(), dto);

            objects.add(record);
        }

        records.put(new TopicPartition(topic, 0), objects);
        return new ConsumerRecords<>(records);
    }

    public static BookEntity toBookEntity(SubscriptionBookDto dto1, Long bookId) {
        return BookEntity.builder()
                .bookId(bookId)
                .bookName(dto1.getBookName())
                .bookAuthor(dto1.getBookAuthor())
                .build();
    }

    public static SubscriptionEntity toSubscriptionEntity(SubscriptionBookDto dto0, Long subscriptionId) {
        return SubscriptionEntity.builder()
                .subscriptionId(subscriptionId)
                .username(dto0.getUsername())
                .userFullName(dto0.getUserFullName())
                .build();
    }

    public static BookEntryEntity toBookEntryEntity(SubscriptionBookDto dto,
                                                    Long bookId,
                                                    Long subscriptionId,
                                                    Long entryId) {
        return BookEntryEntity.builder()
                .book(toBookEntity(dto, bookId))
                .subscription(toSubscriptionEntity(dto, subscriptionId))
                .bookEntryId(entryId)
                .build();
    }

    public static List<ReturnReminderProjection> getListOfProjections(){
        ArrayList<ReturnReminderProjection> list = new ArrayList<>();
        list.add(new ReturnReminderProjectionImpl());
        return list;
    }

    public static MockJavaMailSender.Message getMessage() {
        return MockJavaMailSender.Message.builder().build();
    }
}
