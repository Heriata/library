package deveducate.library.services;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MockJavaMailSender {

    public Message createMimeMessage() {
        return new Message();
    }

    public void send(Message message) {
        log.info("Sending email to [{}], subject [{}], message:[{}]",
                message.getEmail(),
                message.getSubject(),
                message.getBody());
    }

    @Setter
    @Getter
    @Builder
    @RequiredArgsConstructor
    @AllArgsConstructor
    public static class Message {
        private String email;
        private String subject;
        private String body;
    }
}
