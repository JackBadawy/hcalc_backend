package com.jack.hedoncalculator.util;

import java.util.Comparator;
import java.util.List;

import com.jack.hedoncalculator.model.HCourseOfAction;

public class UtilityFunctions {
	
	
	
	public static double calculateHedonicValue(HCourseOfAction course) {
		double intensityVal = course.getIntensity() * 4.9;
		double durationVal = course.getDuration() * 3.7;
		double certaintyVal = course.getCertainty() * 2.3;
		double propinquityVal = course.getPropinquity() * 2.3;
		double fecundityVal = course.getFecundity() * 2.3;
		double purityVal = course.getPurity() * 1.5;
		double extentVal = course.getExtent() * 3;
		double publicVal = course.isPublic() ? 3 : 1;
		
        double PrePubAnswer = intensityVal +
               durationVal +
               certaintyVal +
               propinquityVal +
               fecundityVal +
               purityVal +
               extentVal;
        
        return PrePubAnswer * publicVal;
    }
	public static HCourseOfAction findIdealCourse(List<HCourseOfAction> courses) {
        return courses.stream()
            .max(Comparator.comparingDouble(HCourseOfAction::getHedonicValue))
            .orElse(null);
    }

}