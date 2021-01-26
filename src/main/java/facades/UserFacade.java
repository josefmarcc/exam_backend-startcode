package facades;

import DTO.UserDTO;
import entities.Role;
import entities.User;
import errorhandling.DuplicateException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import security.errorhandling.AuthenticationException;

public class UserFacade {

    private static EntityManagerFactory emf;
    private static UserFacade instance;

    private UserFacade() {
    }

    /**
     *
     * @param _emf
     * @return the instance of this facade.
     */
    public static UserFacade getUserFacade(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new UserFacade();
        }
        return instance;
    }

    public User getVeryfiedUser(String username, String password) throws AuthenticationException {
        EntityManager em = emf.createEntityManager();
        User user;
        try {
            user = em.find(User.class, username);
            if (user == null || !user.verifyPassword(password)) {
                throw new AuthenticationException("Invalid user name or password");
            }
        } finally {
            em.close();
        }
        return user;
    }

    public void deleteUser(String name) {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, name);

        if (user == null) {
            System.out.println("Der er sket en fejl");
        } else {

            try {
                em.getTransaction().begin();
                em.remove(user);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        }
    }

    public List<UserDTO> getAllUsers() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<User> q1
                    = em.createQuery("SELECT u from User u", User.class);
            List<User> userList = q1.getResultList();
            List<UserDTO> userListDTO = new ArrayList();
            for (User user : userList) {
                UserDTO userDTO = new UserDTO(user);
                userListDTO.add(userDTO);
            }
            return userListDTO;
        } finally {
            em.close();
        }
    }

    // tilføj noget hvis brugeren allerede eksisterer
    public void addUser(String userName, String password) throws DuplicateException {
        EntityManager em = emf.createEntityManager();
        User user = em.find(User.class, userName);
        if (user == null) {
            user = new User(userName, password);
            try {
                em.getTransaction().begin();
                Role userRole = new Role("user");
                user.addRole(userRole);
                em.persist(user);
                em.getTransaction().commit();
            } finally {
                em.close();
            }
        } else {
            throw new DuplicateException("User already exsist");
        }
    }

}
