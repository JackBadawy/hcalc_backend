package com.jack.hedoncalculator.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.*;
import com.jack.hedoncalculator.util.UtilityFunctions;

import jakarta.persistence.*;

@Entity
@Table(name = "hevent")
@JsonIgnoreProperties(ignoreUnknown = true)
public class HEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "event_id")
    @JsonManagedReference
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<HCourseOfAction> coursesOfAction = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private HCourseOfAction idealCourse;
    
    @Column
    private String createdBy;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    @JsonIgnore
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    @JsonIgnore
    private LocalDateTime updatedAt;
    
    public HEvent() {
        this.coursesOfAction = new ArrayList<>();
    }
    
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

	 @JsonProperty("coursesOfAction")
	    public void setCoursesOfActionFromJson(List<HCourseOfAction> coursesOfAction) {
	        this.coursesOfAction.clear();
	        if (coursesOfAction != null) {
	            coursesOfAction.forEach(this::addCourseOfAction);
	        }
	        updateIdealCourse();
	    }

	    public void addCourseOfAction(HCourseOfAction course) {
	        if (course != null) {
	            course.setEvent(this);
	            course.setHedonicValue();
	            this.coursesOfAction.add(course);
	        }
	    }

	    @PrePersist
	    @PreUpdate
	    public void updateIdealCourse() {
	        if (!this.coursesOfAction.isEmpty()) {
	            this.idealCourse = UtilityFunctions.findIdealCourse(this.coursesOfAction);
	        }
	    }

	public HCourseOfAction getIdealCourse() {
		return idealCourse;
	}

	public void setIdealCourse(HCourseOfAction idealCourse) {
		this.idealCourse = idealCourse;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

}
