package deveducate.library.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import deveducate.library.aspect.Benchmark;
import deveducate.library.dtos.SubscriptionBookContainerDto;
import deveducate.library.dtos.SubscriptionBookDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExcelDataHandlerService {

    private final ObjectMapper objectMapper;
    private final KafkaTemplate<String, SubscriptionBookDto> kafkaTemplate;

    @Value("${spring.kafka.consumer.group-id}")
    private String LIBRARY_GROUP_ID;

    @Async
    @Benchmark
    @Transactional
    public void sendDataToKafka(MultipartFile file) {
        try {
            sendDataToTopic(fixBrokenJSON(new String(file.getBytes())));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    @Benchmark
    @Transactional
    public void sendDataToKafka(String file) {
        try {
            sendDataToTopic(fixBrokenJSON(file));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void sendDataToTopic(String file) throws JsonProcessingException {
        SubscriptionBookContainerDto dto = objectMapper.readValue(file, SubscriptionBookContainerDto.class);
        dto.getData().forEach(x -> kafkaTemplate.send(LIBRARY_GROUP_ID, x.getUsername(), x));
    }

    private String fixBrokenJSON(String file) {
        return new String(file.getBytes())
                .replace("data", "\"data\"")
                .replaceAll("”", "\"")
                .replaceAll("“", "\"")
                .replaceAll("true", "\"true\"")
                .replaceAll("false", "\"false\"");
    }
}