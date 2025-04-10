// JournalServiceImpl.java
package com.journaling.events.service;

import com.journaling.events.dto.EventResponse;
import com.journaling.events.repository.UserEventRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JournalServiceImpl implements JournalService {
    private  UserEventRepository eventRepository;
    private  ModelMapper modelMapper;

    public UserEventRepository getEventRepository() {
        return eventRepository;
    }

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Override
    public List<EventResponse> getAllEvents() {
        return eventRepository.findAll().stream()
            .map(event -> modelMapper.map(event, EventResponse.class))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<EventResponse> getEventsByUserId(Long userId) {
        return eventRepository.findByUserId(userId).stream()
            .map(event -> modelMapper.map(event, EventResponse.class))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<EventResponse> getEventsByType(String eventType) {
        return eventRepository.findByEventType(eventType).stream()
            .map(event -> modelMapper.map(event, EventResponse.class))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<EventResponse> getEventsBetweenDates(LocalDateTime start, LocalDateTime end) {
        return eventRepository.findEventsBetweenDates(start, end).stream()
            .map(event -> modelMapper.map(event, EventResponse.class))
            .collect(Collectors.toList());
    }
    
    @Override
    public List<EventResponse> getUserEventsBetweenDates(Long userId, LocalDateTime start, LocalDateTime end) {
        return eventRepository.findUserEventsBetweenDates(userId, start, end).stream()
            .map(event -> modelMapper.map(event, EventResponse.class))
            .collect(Collectors.toList());
    }
}