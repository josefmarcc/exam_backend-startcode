/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.ClassEntity;
import entities.Course;
import errorhandling.DuplicateException;
import errorhandling.NotFoundException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import utils.EMF_Creator;

/**
 *
 * @author Josef Marc Pedersen <cph-jp325@cphbusiness.dk>
 */
public class CourseFacadeTest {

    private static EntityManagerFactory emf;
    private static CourseFacade facade;
    private Course course1, course2;
    private ClassEntity classEntity;

    public CourseFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = CourseFacade.getCourseFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        course1 = new Course("JavaScript", "Learn to code in JS");
        course2 = new Course("Python", "Learn to code in Python");

        try {
            em.getTransaction().begin();
            em.createQuery("Delete from ClassEntity").executeUpdate();
            em.createQuery("Delete from Course").executeUpdate();
            classEntity = new ClassEntity(1, 20);
            classEntity.setCourse(course1);
            em.persist(course1);
            em.persist(course2);
            em.persist(classEntity);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetAllUsers() {
        assertEquals(2, facade.getAllCourses().size(), "Expects two rows in the database");
    }

    /**
     * Test of addCourse method, of class CourseFacade.
     *
     * @throws errorhandling.DuplicateException
     */
    @Test
    public void testAddCourse() throws DuplicateException {
        System.out.println("TESTING SIZE AFTER ADD METHOD ....");

        facade.addCourse("Java", "Learn To code in Java");
        assertEquals(3, facade.getAllCourses().size(), "Expects three rows in the database");
    }

    @Test
    public void testGetCourseByName() throws NotFoundException {
        String description = facade.getCourseByName("JavaScript").getDescription();
        assertEquals("Learn to code in JS", description);

    }

    @Test
    public void testAddClassToCourse() throws NotFoundException {
        System.out.println("TESTING Name of Class entity after add");

        facade.addClassToCourse(2, 20, course1.getCourseName());
        assertEquals(20, facade.getClassBySemester(2).getNumberOfStudents());
    }

    @Test
    public void testGetAllClasses() {
        assertEquals(1, facade.getAllClasses().size(), "Expects one row in the database");
    }

}
