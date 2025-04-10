package com.journaling.events.repository;

import com.journaling.events.entity.UserEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
@Repository
public interface UserEventRepository extends JpaRepository<UserEvent, Long> {
    List<UserEvent> findByUserId(Long userId);
    List<UserEvent> findByEventType(String eventType);
    
    @Query("SELECT e FROM UserEvent e WHERE e.timestamp BETWEEN :start AND :end")
    List<UserEvent> findEventsBetweenDates(
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
    
    @Query("SELECT e FROM UserEvent e WHERE e.userId = :userId AND e.timestamp BETWEEN :start AND :end")
    List<UserEvent> findUserEventsBetweenDates(
        @Param("userId") Long userId,
        @Param("start") LocalDateTime start,
        @Param("end") LocalDateTime end
    );
}