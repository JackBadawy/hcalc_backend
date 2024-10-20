//package com.jack.hedoncalculator.controller;
//
//import com.jack.hedoncalculator.model.HEvent;
//import com.jack.hedoncalculator.model.HCourseOfAction;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.test.web.server.LocalServerPort;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.*;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@ActiveProfiles("test")
//public class HEventControllerIntegrationTest {
//
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    private String getRootUrl() {
//        return "http://localhost:" + port + "/api/events";
//    }
//
//    @Test
//    public void testCRUDOperations() {
//        HEvent hEvent = createSampleHEvent();
//        ResponseEntity<HEvent> postResponse = restTemplate.postForEntity(getRootUrl(), hEvent, HEvent.class);
//        assertNotNull(postResponse);
//        assertNotNull(postResponse.getBody());
//        assertEquals(HttpStatus.OK, postResponse.getStatusCode());
//        Long eventId = postResponse.getBody().getId();
//
//        HEvent getResponse = restTemplate.getForObject(getRootUrl() + "/" + eventId, HEvent.class);
//        assertNotNull(getResponse);
//        assertEquals("Test Event", getResponse.getDescription());
//
//        getResponse.setDescription("Updated Test Event");
//        restTemplate.put(getRootUrl() + "/" + eventId, getResponse);
//        HEvent updatedEvent = new HEvent();
//        updatedEvent.setDescription("Updated Test Event");
//        updatedEvent.setCoursesOfAction(getResponse.getCoursesOfAction()); 
//        restTemplate.put(getRootUrl() + "/" + eventId, updatedEvent);
//
//        
//        restTemplate.delete(getRootUrl() + "/" + eventId);
//        ResponseEntity<HEvent> deleteResponse = restTemplate.getForEntity(getRootUrl() + "/" + eventId, HEvent.class);
//        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());
//    }
//
//    private HEvent createSampleHEvent() {
//        HEvent event = new HEvent();
//        event.setDescription("Test Event");
//
//        List<HCourseOfAction> courses = new ArrayList<>();
//        HCourseOfAction course = new HCourseOfAction();
//        course.setDescription("Test Course of Action");
//        course.setIntensity(5.0);
//        course.setDuration(3.0);
//        course.setCertainty(0.8);
//        course.setPropinquity(2.0);
//        course.setFecundity(1.5);
//        course.setPurity(0.9);
//        course.setExtent(10.0);
//        course.setPublic(true);
//        courses.add(course);
//
//        event.setCoursesOfAction(courses);
//
//        return event;
//    }
//}