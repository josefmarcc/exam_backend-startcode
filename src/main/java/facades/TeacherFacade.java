package facades;

import DTO.TeacherDTO;
import entities.Teacher;
import errorhandling.DuplicateException;
import errorhandling.NotFoundException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

public class TeacherFacade {

    private static EntityManagerFactory emf;
    private static TeacherFacade instance;

    private TeacherFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static TeacherFacade getTeacherFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new TeacherFacade();
        }
        return instance;
    }

    public void addTeacher(TeacherDTO t) throws DuplicateException, NotFoundException {
        if ((t.getName().length() == 0)) {
            throw new NotFoundException("Name input missing");
        }

        EntityManager em = emf.createEntityManager();
        Teacher teacher = em.find(Teacher.class, t.getId());
        if (teacher == null) {
            teacher = new Teacher(t.getName(), t.getEmail());
            try {
                em.getTransaction().begin();
                em.persist(teacher);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        } else {
            throw new DuplicateException("Teacher already exsist");
        }
    }

    public List<TeacherDTO> getAllTeachers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Teacher> q1
                    = em.createQuery("SELECT t from Teacher t", Teacher.class);
            List<Teacher> teacherList = q1.getResultList();
            List<TeacherDTO> teacherListDTO = new ArrayList();
            for (Teacher teacher : teacherList) {
                TeacherDTO teacherDTO = new TeacherDTO(teacher);
                teacherListDTO.add(teacherDTO);
            }
            return teacherListDTO;
        } finally {
            em.close();
        }
    }

//    public TeacherDTO addClassToTeacher(int teacherId, int classEntity) throws NotFoundException {
//        if ((teacherId == 0)) {
//            throw new NotFoundException("Teacher id missing");
//        }
//        EntityManager em = emf.createEntityManager();
//
//        try {
//            em.getTransaction().begin();
//
//            Teacher teacher = em.find(Teacher.class, teacherId);
//            ClassEntity ce = em.find(ClassEntity.class, classEntity);
//            if (teacher == null) {
//                throw new NotFoundException(String.format("Teacher with id: (%s) not found", teacherId));
//            } else {
//                teacher.addClassEntity(ce);
//            }
//            em.getTransaction().commit();
//            return new TeacherDTO(teacher);
//        } finally {
//            em.close();
//        }
//    }
}
