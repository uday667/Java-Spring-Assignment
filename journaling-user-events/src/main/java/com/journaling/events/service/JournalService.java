// JournalService.java
package com.journaling.events.service;

import com.journaling.events.dto.EventResponse;

import java.time.LocalDateTime;
import java.util.List;

public interface JournalService {
    List<EventResponse> getAllEvents();
    List<EventResponse> getEventsByUserId(Long userId);
    List<EventResponse> getEventsByType(String eventType);
    List<EventResponse> getEventsBetweenDates(LocalDateTime start, LocalDateTime end);
    List<EventResponse> getUserEventsBetweenDates(Long userId, LocalDateTime start, LocalDateTime end);
}
