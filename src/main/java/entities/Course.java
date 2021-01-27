package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
@Entity
public class Course implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private String courseName;

    private String description;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    List<ClassEntity> classList;

    public Course(String courseName, String description) {
        this.courseName = courseName;
        this.description = description;
    }

    public Course() {
    }

    public List<ClassEntity> getClassList() {
        return classList;
    }

    public void addClassEntity(ClassEntity classEntity) {
        this.classList.add(classEntity);
        if (classEntity != null) {
            classEntity.setCourse(this);
        }
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
