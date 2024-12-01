package com.jack.hedoncalculator.controller;

import com.jack.hedoncalculator.model.HEvent;
import com.jack.hedoncalculator.service.HEventService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class HEventController {
    private static final Logger logger = LoggerFactory.getLogger(HEventController.class);
    
    private final HEventService hEventService;

    @Autowired
    public HEventController(HEventService hEventService) {
        this.hEventService = hEventService;
    }

    @GetMapping
    public List<HEvent> getAllEvents(@RequestHeader("Authorization") String authHeader) {
        String sessionToken = extractToken(authHeader);
        return hEventService.getUsersEvents(sessionToken);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HEvent> getEventById(@PathVariable Long id, 
                                             @RequestHeader("Authorization") String authHeader) {
        String sessionToken = extractToken(authHeader);
        return hEventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<HEvent> addEvent(@RequestBody HEvent event,
                                         @RequestHeader("Authorization") String authHeader) {
        try {
            String sessionToken = extractToken(authHeader);
            
            logger.debug("Received event request with description: {}", 
                event != null ? event.getDescription() : "null");
            logger.debug("Courses of action count: {}", 
                event != null && event.getCoursesOfAction() != null ? 
                event.getCoursesOfAction().size() : "null");
            
            if (event == null) {
                logger.error("Event object is null");
                return ResponseEntity.badRequest().build();
            }

            if (event.getCoursesOfAction() == null) {
                logger.error("Courses of action is null");
                return ResponseEntity.badRequest().build();
            }

            HEvent savedEvent = hEventService.addEvent(event, sessionToken);
            logger.debug("Successfully saved event with id: {}", savedEvent.getId());
            return ResponseEntity.ok(savedEvent);
            
        } catch (IllegalArgumentException e) {
            logger.error("Invalid session token or unauthorized access: ", e);
            return ResponseEntity.status(403).build();
        } catch (Exception e) {
            logger.error("Error processing event: ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HEvent> updateEvent(@PathVariable Long id, 
                                            @RequestBody HEvent event,
                                            @RequestHeader("Authorization") String authHeader) {
        try {
            String sessionToken = extractToken(authHeader);
            return hEventService.updateEvent(id, event, sessionToken)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
        } catch (IllegalArgumentException e) {
            logger.error("Invalid session token or unauthorized access: ", e);
            return ResponseEntity.status(403).build();
        } catch (Exception e) {
            logger.error("Error updating event: ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id,
                                          @RequestHeader("Authorization") String authHeader) {
        try {
            String sessionToken = extractToken(authHeader);
            if (hEventService.deleteEvent(id)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            logger.error("Invalid session token or unauthorized access: ", e);
            return ResponseEntity.status(403).build();
        } catch (Exception e) {
            logger.error("Error deleting event: ", e);
            return ResponseEntity.badRequest().build();
        }
    }

    private String extractToken(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        throw new IllegalArgumentException("Invalid authorization header");
    }
}