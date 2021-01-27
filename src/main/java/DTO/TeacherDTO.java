package DTO;

import entities.Teacher;
import java.util.List;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class TeacherDTO {

    private int id;
    private String name;
    private String email;
    private List classEntityList;

    public TeacherDTO(Teacher teacher) {
        this.id = teacher.getId();
        this.name = teacher.getName();
        this.email = teacher.getEmail();
        this.classEntityList = teacher.getClassEntityList();
    }

    public List getClassEntityList() {
        return classEntityList;
    }

    public void setClassEntityList(List classEntityList) {
        this.classEntityList = classEntityList;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
