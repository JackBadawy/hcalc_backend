package com.jack.hedoncalculator.dto;

import java.util.List;

public class HEventDTO {
    private Long id;
    private String description;
    private List<HCourseOfActionDTO> coursesOfAction;
    private HCourseOfActionDTO idealCourse;
    
    
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public List<HCourseOfActionDTO> getCoursesOfAction() {
		return coursesOfAction;
	}
	public void setCoursesOfAction(List<HCourseOfActionDTO> coursesOfAction) {
		this.coursesOfAction = coursesOfAction;
	}
	public HCourseOfActionDTO getIdealCourse() {
		return idealCourse;
	}
	public void setIdealCourse(HCourseOfActionDTO idealCourse) {
		this.idealCourse = idealCourse;
	}

    
}
