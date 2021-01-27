package DTO;

import entities.Course;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class CourseDTO {

    private String courseName;
    private String description;

    public CourseDTO(Course course) {
        this.courseName = course.getCourseName();
        this.description = course.getDescription();
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
