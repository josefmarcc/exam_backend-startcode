package facades;

import DTO.CourseDTO;
import DTO.UserDTO;
import entities.Course;
import entities.User;
import errorhandling.DuplicateException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class CourseFacade {

    private static EntityManagerFactory emf;
    private static CourseFacade instance;

    private CourseFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static CourseFacade getCourseFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new CourseFacade();
        }
        return instance;
    }

    public void addCourse(String courseName, String desc) throws DuplicateException {
        EntityManager em = emf.createEntityManager();
        Course course = em.find(Course.class, courseName);
        if (course == null) {
            course = new Course(courseName, desc);
            try {
                em.getTransaction().begin();
                em.persist(course);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        } else {
            throw new DuplicateException("Course already exsist");
        }
    }

    public List<CourseDTO> getAllCourses() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Course> q1
                    = em.createQuery("SELECT c from Course c", Course.class);
            List<Course> courseList = q1.getResultList();
            List<CourseDTO> courseListDTO = new ArrayList();
            for (Course course : courseList) {
                CourseDTO courseDTO = new CourseDTO(course);
                courseListDTO.add(courseDTO);
            }
            return courseListDTO;
        } finally {
            em.close();
        }
    }

}
