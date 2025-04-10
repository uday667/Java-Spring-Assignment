package com.user.management.event;

import java.time.LocalDateTime;

public class UserEvent {
    private final Long userId;
    private final String username;
    private final String eventType;
    private final LocalDateTime timestamp;

    private UserEvent(Builder builder) {
        this.userId = builder.userId;
        this.username = builder.username;
        this.eventType = builder.eventType;
        this.timestamp = builder.timestamp;
    }

    // Getters
    public Long getUserId() { return userId; }
    public String getUsername() { return username; }
    public String getEventType() { return eventType; }
    public LocalDateTime getTimestamp() { return timestamp; }

    // Builder class
    public static class Builder {
        private Long userId;
        private String username;
        private String eventType;
        private LocalDateTime timestamp;

        public Builder userId(Long userId) {
            this.userId = userId;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder eventType(String eventType) {
            this.eventType = eventType;
            return this;
        }

        public Builder timestamp(LocalDateTime timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public UserEvent build() {
            return new UserEvent(this);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
}