package com.journaling.events.dto;

import java.time.LocalDateTime;


public class EventResponse {
    private Long id;
    private Long userId;
    private String username;
    private String eventType;
    private LocalDateTime timestamp;
    private LocalDateTime receivedAt;

    public EventResponse() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setReceivedAt(LocalDateTime receivedAt) {
        this.receivedAt = receivedAt;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEventType() {
        return eventType;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public LocalDateTime getReceivedAt() {
        return receivedAt;
    }
}