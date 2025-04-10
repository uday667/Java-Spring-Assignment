package com.user.management.event;

import com.user.management.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserEventPublisher {
    private static final String TOPIC = "user-events";

    private final KafkaTemplate<String, UserEvent> kafkaTemplate;

    public UserEventPublisher(KafkaTemplate<String, UserEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }


    public void publishUserEvent(User user, String eventType) {
        UserEvent event = UserEvent.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .eventType(eventType)
                .timestamp(LocalDateTime.now())
                .build();

        kafkaTemplate.send(TOPIC, user.getId().toString(), event);
    }
}