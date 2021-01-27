package DTO;

import entities.ClassEntity;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class ClassEntityDTO {

    private int id;

    private int semester;

    private int numberOfStudents;

    private String courseName;

    private String description;

    public ClassEntityDTO(ClassEntity classEntity) {
        this.semester = classEntity.getSemester();
        this.numberOfStudents = classEntity.getNumberOfStudents();
        this.courseName = classEntity.getCourse().getCourseName();
        this.description = classEntity.getCourse().getDescription();
        this.id = classEntity.getId();
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public int getNumberOfStudents() {
        return numberOfStudents;
    }

    public void setNumberOfStudents(int numberOfStudents) {
        this.numberOfStudents = numberOfStudents;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
