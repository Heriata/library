package deveducate.library.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import deveducate.library.aspect.Benchmark;
import deveducate.library.dtos.BookDto;
import deveducate.library.dtos.BookEntryDto;
import deveducate.library.dtos.SubscriptionBookContainerDto;
import deveducate.library.dtos.SubscriptionBookDataContainerDto;
import deveducate.library.dtos.SubscriptionBookDto;
import deveducate.library.dtos.SubscriptionDto;
import deveducate.library.repositories.BookEntryRepository;
import deveducate.library.repositories.BookRepository;
import deveducate.library.repositories.SubscriptionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelDataHandlerService {

    private final ObjectMapper objectMapper;
    private final SubscriptionRepository subscriptionRepository;
    private final BookRepository bookRepository;
    private final BookEntryRepository bookEntryRepository;
    private final KafkaTemplate<String, SubscriptionBookDto> kafkaTemplate;

    @Value("${spring.kafka.consumer.group-id}")
    private String LIBRARY_GROUP_ID;

    @Async
    @Benchmark
    @Transactional
    public void sendDataToKafka(MultipartFile file) {
        try {
            String string = fixBrokenJSON(file);
            SubscriptionBookContainerDto dto = objectMapper.readValue(string, SubscriptionBookContainerDto.class);
            dto.getData().forEach(x -> kafkaTemplate.send(LIBRARY_GROUP_ID, x.getUsername(), x));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static Map<SubscriptionDto, List<BookEntryDto>> getSubscriptionDtoListMap(SubscriptionBookContainerDto dto) {
        return dto.getData().stream()
                .map(x -> SubscriptionBookDataContainerDto.builder()
                        .bookEntryDto(BookEntryDto.builder()
                                .book(BookDto.builder()
                                        .bookName(x.getBookName())
                                        .bookAuthor(x.getBookAuthor())
                                        .build())
                                .build())
                        .subscriptionDto(SubscriptionDto.builder()
                                .username(x.getUsername())
                                .userFullName(x.getUserFullName())
                                .userActive(x.isUserActive())
                                .build())
                        .build())
                .collect(Collectors.groupingBy(SubscriptionBookDataContainerDto::getSubscriptionDto,
                        Collectors.mapping(SubscriptionBookDataContainerDto::getBookEntryDto, Collectors.toList())));
    }

    private static String fixBrokenJSON(MultipartFile file) throws IOException {
        return new String(file.getBytes())
                .replace("data", "\"data\"")
                .replaceAll("”", "\"")
                .replaceAll("“", "\"")
                .replaceAll("true", "\"true\"")
                .replaceAll("false", "\"false\"");
    }
}

//            SubscriptionEntity {
//                subscriptionId
//                username
//                userFullName
//                userActive
//                book<BookEntryEntity> OneToMany{
//                      bookEntryId
//                      timestamp
//                      subscription<SubscriptionEntity> ManyToOne
//                      book<BookEntity> OneToOne {
//                              bookId
//                              bookName
//                              bookAuthor
//                              publishingDate
//                      }
//                 }
