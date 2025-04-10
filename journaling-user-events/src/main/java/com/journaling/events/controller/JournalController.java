package com.journaling.events.controller;

import com.journaling.events.dto.EventResponse;
import com.journaling.events.service.JournalService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/events")
public class JournalController {
    private final JournalService journalService;
    
    public JournalController(JournalService journalService) {
        this.journalService = journalService;
    }
    
    @GetMapping
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EventResponse>> getAllEvents() {
        return ResponseEntity.ok(journalService.getAllEvents());
    }
    
    @GetMapping("/user/{userId}")
//    @PreAuthorize("hasRole('ADMIN') or (authentication.principal == @userEventRepository.findUsernameByUserId(#userId))")
    public ResponseEntity<List<EventResponse>> getEventsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(journalService.getEventsByUserId(userId));
    }
    
    @GetMapping("/type/{eventType}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EventResponse>> getEventsByType(@PathVariable String eventType) {
        return ResponseEntity.ok(journalService.getEventsByType(eventType));
    }
    
    @GetMapping("/date-range")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<EventResponse>> getEventsBetweenDates(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(journalService.getEventsBetweenDates(start, end));
    }
    
    @GetMapping("/user/{userId}/date-range")
    @PreAuthorize("hasRole('ADMIN') or (authentication.principal == @userEventRepository.findUsernameByUserId(#userId))")
    public ResponseEntity<List<EventResponse>> getUserEventsBetweenDates(
            @PathVariable Long userId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return ResponseEntity.ok(journalService.getUserEventsBetweenDates(userId, start, end));
    }
}