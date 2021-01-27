package entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
@Entity
public class ClassEntity implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    private int semester;

    private int numberOfStudents;

    @ManyToOne(cascade = CascadeType.ALL)
    private Course course;

    public ClassEntity(int semester, int numberOfStudents) {
        this.semester = semester;
        this.numberOfStudents = numberOfStudents;
    }

    public ClassEntity() {
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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

}
