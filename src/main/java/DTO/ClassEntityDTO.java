package DTO;

import entities.ClassEntity;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class ClassEntityDTO {

    private int semester;

    private int numberOfStudents;

    private String courseName;

    public ClassEntityDTO(ClassEntity classEntity) {
        this.semester = classEntity.getSemester();
        this.numberOfStudents = classEntity.getNumberOfStudents();

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

}
