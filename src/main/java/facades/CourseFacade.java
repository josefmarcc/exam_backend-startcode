package facades;

import DTO.ClassEntityDTO;
import DTO.CourseDTO;
import entities.ClassEntity;
import entities.Course;
import errorhandling.DuplicateException;
import errorhandling.NotFoundException;
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

    public void addClassToCourse(int semester, int numOfStudents, String courseName) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Course course = em.find(Course.class, courseName);

        if (course == null) {
            throw new NotFoundException("Cannot find course");
        } else {

            try {
                em.getTransaction().begin();
                ClassEntity ce = new ClassEntity(semester, numOfStudents);
                ce.setCourse(course);
                em.persist(ce);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
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

    public CourseDTO getCourseByName(String courseName) throws NotFoundException {
        EntityManager em = emf.createEntityManager();
        Course course;
        try {
            course = em.find(Course.class, courseName);
            if (course == null) {
                throw new NotFoundException("Cannot find course");
            } else {
                return new CourseDTO(course);
            }
        } finally {
            em.close();
        }
    }

    public List<ClassEntityDTO> getClassBySemester(int semester) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<ClassEntity> q1
                    = em.createQuery("SELECT s FROM ClassEntity s WHERE s.semester LIKE :semester", ClassEntity.class);
            List<ClassEntity> classEntityList = q1.setParameter("semester", semester).getResultList();
            List<ClassEntityDTO> classListDTO = new ArrayList();
            for (ClassEntity classEntity : classEntityList) {
                ClassEntityDTO classEntityDTO = new ClassEntityDTO(classEntity);
                classListDTO.add(classEntityDTO);
            }
            return classListDTO;
        } finally {
            em.close();
        }
    }

    public List<ClassEntityDTO> getAllClasses() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<ClassEntity> q1
                    = em.createQuery("SELECT c from ClassEntity c", ClassEntity.class);
            List<ClassEntity> classList = q1.getResultList();
            List<ClassEntityDTO> classListDTO = new ArrayList();
            for (ClassEntity classEntity : classList) {
                ClassEntityDTO classEntityDTO = new ClassEntityDTO(classEntity);
                classListDTO.add(classEntityDTO);
            }
            return classListDTO;
        } finally {
            em.close();
        }
    }

}
