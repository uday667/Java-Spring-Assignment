package com.journaling.events.service;


import com.journaling.events.entity.UserEvent;
import com.journaling.events.repository.UserEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EventConsumer {
    private final UserEventRepository eventRepository;

    public EventConsumer(UserEventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @KafkaListener(topics = "user-events", groupId = "journal-group")
    @Transactional
    public void consume(UserEvent incomingEvent) {
        log.info("Received user event: {}", incomingEvent);
        UserEvent event = new UserEvent();
        event.setUserId(incomingEvent.getUserId());
        event.setUsername(incomingEvent.getUsername());
        event.setEventType(incomingEvent.getEventType());
        event.setTimestamp(incomingEvent.getTimestamp());
        eventRepository.save(event);
        log.info("Persisted user event with ID: {}", event.getId());
    }
}