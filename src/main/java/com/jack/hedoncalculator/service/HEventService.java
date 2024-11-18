package com.jack.hedoncalculator.service;

import com.jack.hedoncalculator.model.HEvent;
import com.jack.hedoncalculator.model.HCourseOfAction;
import com.jack.hedoncalculator.repository.HEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
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

    @Transactional
    public HEvent addEvent(HEvent event) {
        HEvent newEvent = new HEvent();
        newEvent.setDescription(event.getDescription());
        
        List<HCourseOfAction> newCourses = new ArrayList<>();
        for (HCourseOfAction course : event.getCoursesOfAction()) {
            HCourseOfAction newCourse = new HCourseOfAction();
            newCourse.setDescription(course.getDescription());
            newCourse.setIntensity(course.getIntensity());
            newCourse.setDuration(course.getDuration());
            newCourse.setCertainty(course.getCertainty());
            newCourse.setPropinquity(course.getPropinquity());
            newCourse.setFecundity(course.getFecundity());
            newCourse.setPurity(course.getPurity());
            newCourse.setExtent(course.getExtent());
            newCourse.setPublic(course.isPublic());
            newCourse.setHedonicValue();
            newCourse.setEvent(newEvent);
            newCourses.add(newCourse);
        }
        
        newEvent.setCoursesOfActionFromJson(newCourses);
        newEvent.updateIdealCourse();
        
        return hEventRepository.save(newEvent);
    }

    @Transactional
    public Optional<HEvent> updateEvent(Long id, HEvent eventDetails) {
        return hEventRepository.findById(id)
            .map(existingEvent -> {
                existingEvent.setDescription(eventDetails.getDescription());
                List<HCourseOfAction> updatedCourses = new ArrayList<>();
                for (HCourseOfAction course : eventDetails.getCoursesOfAction()) {
                    HCourseOfAction updatedCourse = new HCourseOfAction();
                    updatedCourse.setDescription(course.getDescription());
                    updatedCourse.setIntensity(course.getIntensity());
                    updatedCourse.setDuration(course.getDuration());
                    updatedCourse.setCertainty(course.getCertainty());
                    updatedCourse.setPropinquity(course.getPropinquity());
                    updatedCourse.setFecundity(course.getFecundity());
                    updatedCourse.setPurity(course.getPurity());
                    updatedCourse.setExtent(course.getExtent());
                    updatedCourse.setPublic(course.isPublic());
                    updatedCourse.setHedonicValue();
                    updatedCourse.setEvent(existingEvent);
                    updatedCourses.add(updatedCourse);
                }
                existingEvent.setCoursesOfActionFromJson(updatedCourses);
                existingEvent.updateIdealCourse();
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