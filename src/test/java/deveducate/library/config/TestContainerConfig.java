package deveducate.library.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

@Slf4j
@Testcontainers
@SpringBootTest
@AutoConfigureMockMvc
public class TestContainerConfig {

    protected static final ObjectMapper objectMapper = new ObjectMapper();

    @Container
    public static PostgreSQLContainer<?> psqlContainer = new PostgreSQLContainer<>("postgres:17-alpine")
            .withDatabaseName("library")
            .withUsername("library")
            .withPassword("library")
            .withExposedPorts(5432);

    @Container
    public static KafkaContainer kafkaContainer = new KafkaContainer("apache/kafka:latest")
            .withExposedPorts(9092);

    @DynamicPropertySource
    public static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", psqlContainer::getJdbcUrl);
        registry.add("spring.datasource.password", psqlContainer::getPassword);
        registry.add("spring.datasource.username", psqlContainer::getUsername);

        registry.add("spring.liquibase.url", psqlContainer::getJdbcUrl);
        registry.add("spring.liquibase.password", psqlContainer::getPassword);
        registry.add("spring.liquibase.user", psqlContainer::getUsername);

        registry.add("spring.kafka.consumer.bootstrap-servers", kafkaContainer::getBootstrapServers);
        registry.add("spring.kafka.consumer.bootstrap-servers", kafkaContainer::getBootstrapServers);
    }
}
