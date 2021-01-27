/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import DTO.TeacherDTO;
import entities.Teacher;
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
public class TeacherFacadeTest {

    private static EntityManagerFactory emf;
    private static TeacherFacade facade;
    private Teacher teacher1, teacher2;

    public TeacherFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = TeacherFacade.getTeacherFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        teacher1 = new Teacher("Josef", "cph-jp325@cphbusiness.dk");

        try {
            em.getTransaction().begin();
            em.createQuery("Delete from Teacher").executeUpdate();
            em.persist(teacher1);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetAllTeachers() {
        assertEquals(1, facade.getAllTeachers().size(), "Expects one rows in the database");
    }

    /**
     * Test of addTeacher method, of class TeacherFacade.
     *
     * @throws errorhandling.DuplicateException
     * @throws errorhandling.NotFoundException
     */
    @Test
    public void testAddTeacher() throws DuplicateException, NotFoundException {
        System.out.println("TESTING SIZE AFTER ADD METHOD ....");
        teacher2 = new Teacher("Test", "test@cphbusiness.dk");
        teacher2.setId(55);
        TeacherDTO teacherDTO = new TeacherDTO(teacher2);

        facade.addTeacher(teacherDTO);
        assertEquals(2, facade.getAllTeachers().size(), "Expects three rows in the database");
    }

}
