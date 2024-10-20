package com.jack.hedoncalculator.util;

import java.util.Comparator;
import java.util.List;

import com.jack.hedoncalculator.model.HCourseOfAction;

public class UtilityFunctions {
	public static double calculateHedonicValue(HCourseOfAction course) {
        return course.getIntensity() +
               course.getDuration() +
               course.getCertainty() +
               course.getPropinquity() +
               course.getFecundity() +
               course.getPurity() +
               course.getExtent();
    }
	public static HCourseOfAction findIdealCourse(List<HCourseOfAction> courses) {
        return courses.stream()
            .max(Comparator.comparingDouble(HCourseOfAction::getHedonicValue))
            .orElse(null);
    }

}