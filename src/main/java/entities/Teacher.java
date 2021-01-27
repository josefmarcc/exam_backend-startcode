package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
@Entity
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String email;

    @ManyToMany(mappedBy = "teacherList")
    private List<ClassEntity> classEntityList = new ArrayList<>();

    public Teacher(String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.classEntityList = new ArrayList<>();
    }

    public Teacher() {
    }

    public List<ClassEntity> getClassEntityList() {
        return classEntityList;
    }

    public void setClassEntityList(List<ClassEntity> classEntityList) {
        this.classEntityList = classEntityList;
    }

    public void addClassEntity(ClassEntity classEntity) {
        classEntityList.add(classEntity);
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}
