/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entities.Role;
import entities.User;
import errorhandling.DuplicateException;
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
public class UserFacadeTest {

    private static EntityManagerFactory emf;
    private static UserFacade facade;
    private User user1, user2;
    private Role userRole, adminRole;

    public UserFacadeTest() {
    }

    @BeforeAll
    public static void setUpClass() {
        emf = EMF_Creator.createEntityManagerFactoryForTest();
        facade = UserFacade.getUserFacade(emf);
    }

    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();

        user1 = new User("user", "kode123");
        user2 = new User("admin", "kodetest");
        userRole = new Role("user");
        adminRole = new Role("admin");

        try {
            em.getTransaction().begin();
            em.createQuery("Delete from User").executeUpdate();
            em.createQuery("DELETE from Role").executeUpdate();
            user1.addRole(userRole);
            user2.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user1);
            em.persist(user2);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    @Test
    public void testGetAllUsers() {
        assertEquals(2, facade.getAllUsers().size(), "Expects two rows in the database");
    }

    /**
     * Test of addUser method, of class UserFacade.
     */
    //@Disabled
    @Test
    public void testAddUser() throws DuplicateException {
        System.out.println("TESTING SIZE AFTER ADD METHOD ....");

        String userName = "user3";
        String userPassword = "thisIsASecretPassWord1";

        facade.addUser(userName, userPassword);
        System.out.println("TESTING SIZE AFTER ADD METHOD");
        assertEquals(3, facade.getAllUsers().size(), "Expects three rows in the database");
    }

}
