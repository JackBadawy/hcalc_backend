package com.jack.hedoncalculator.controller;

import com.jack.hedoncalculator.model.HEvent;
import com.jack.hedoncalculator.service.HEventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class HEventController {
    private final HEventService hEventService;

    @Autowired
    public HEventController(HEventService hEventService) {
        this.hEventService = hEventService;
    }

    @GetMapping
    public List<HEvent> getAllEvents() {
        return hEventService.getAllEvents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<HEvent> getEventById(@PathVariable Long id) {
        return hEventService.getEventById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public HEvent addEvent(@RequestBody HEvent event) {
        return hEventService.addEvent(event);
    }

    @PutMapping("/{id}")
    public ResponseEntity<HEvent> updateEvent(@PathVariable Long id, @RequestBody HEvent event) {
        return hEventService.updateEvent(id, event)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEvent(@PathVariable Long id) {
        if (hEventService.deleteEvent(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}