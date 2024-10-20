package com.jack.hedoncalculator.service;

import com.jack.hedoncalculator.model.HEvent;
import com.jack.hedoncalculator.repository.HEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HEventService {
    private final HEventRepository hEventRepository;

    @Autowired
    public HEventService(HEventRepository hEventRepository) {
        this.hEventRepository = hEventRepository;
    }

    public List<HEvent> getAllEvents() {
        return hEventRepository.findAll();
    }

    public Optional<HEvent> getEventById(Long id) {
        return hEventRepository.findById(id);
    }

    public HEvent addEvent(HEvent event) {
        return hEventRepository.save(event);
    }

    public Optional<HEvent> updateEvent(Long id, HEvent eventDetails) {
        return hEventRepository.findById(id)
            .map(existingEvent -> {
                existingEvent.setDescription(eventDetails.getDescription());
                existingEvent.getCoursesOfAction().clear();
                existingEvent.getCoursesOfAction().addAll(eventDetails.getCoursesOfAction());
                existingEvent.setIdealCourse(eventDetails.getIdealCourse());
                return hEventRepository.save(existingEvent);
            });
    }

    public boolean deleteEvent(Long id) {
        return hEventRepository.findById(id)
            .map(event -> {
                hEventRepository.delete(event);
                return true;
            })
            .orElse(false);
    }
}
