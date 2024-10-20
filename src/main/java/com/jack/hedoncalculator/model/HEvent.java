package com.jack.hedoncalculator.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.jack.hedoncalculator.util.UtilityFunctions;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class HEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<HCourseOfAction> coursesOfAction = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    private HCourseOfAction idealCourse;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
	public Long getId() {
		return id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<HCourseOfAction> getCoursesOfAction() {
		return coursesOfAction;
	}

	public void setCoursesOfAction(List<HCourseOfAction> coursesOfAction) {
	    this.coursesOfAction.clear();
	    if (coursesOfAction != null) {
	        this.coursesOfAction.addAll(coursesOfAction);
	    }
	    updateIdealCourse();
	}

	public HCourseOfAction getIdealCourse() {
		return idealCourse;
	}

	public void setIdealCourse(HCourseOfAction idealCourse) {
		this.idealCourse = idealCourse;
	}

	public void updateIdealCourse() {
        this.idealCourse = UtilityFunctions.findIdealCourse(this.coursesOfAction);
    }
}
