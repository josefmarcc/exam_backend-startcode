package DTO;

import entities.ClassEntity;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class ClassEntityDTO {

    private Integer id;

    private int semester;

    private int numberOfStudents;

    public ClassEntityDTO(ClassEntity classEntity) {
        this.id = classEntity.getId();
        this.semester = classEntity.getSemester();
        this.numberOfStudents = classEntity.getNumberOfStudents();

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

}
