package rest;

import entities.Course;
import entities.User;
import entities.Role;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import java.lang.reflect.Array;
import java.net.URI;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import utils.EMF_Creator;

public class CourseResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        //This method must be called before you request the EntityManagerFactory
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        //Setup RestAssured
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        //Don't forget this, if you called its counterpart in @BeforeAll
        EMF_Creator.endREST_TestWithDB();

        httpServer.shutdownNow();
    }

    // Setup the DataBase (used by the test-server and this test) in a known state BEFORE EACH TEST
    //TODO -- Make sure to change the EntityClass used below to use YOUR OWN (renamed) Entity class
    @BeforeEach
    public void setUp() {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            //Delete existing classentites, courses, users and roles to get a "fresh" database
            em.createQuery("delete from ClassEntity").executeUpdate();
            em.createQuery("delete from Course").executeUpdate();
            em.createQuery("delete from User").executeUpdate();
            em.createQuery("delete from Role").executeUpdate();

            Role userRole = new Role("user");
            Role adminRole = new Role("admin");
            User user = new User("user", "test");
            user.addRole(userRole);
            User admin = new User("admin", "test");
            admin.addRole(adminRole);
            User both = new User("user_admin", "test");
            Course course = new Course("JavaScript", "Learn to code in JavaScript");
            both.addRole(userRole);
            both.addRole(adminRole);
            em.persist(userRole);
            em.persist(adminRole);
            em.persist(user);
            em.persist(admin);
            em.persist(both);
            em.persist(course);
            //System.out.println("Saved test data to database");
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    //This is how we hold on to the token after login, similar to that a client must store the token somewhere
    private static String securityToken;

    //Utility method to login and set the returned securityToken
    private static void login(String role, String password) {
        String json = String.format("{username: \"%s\", password: \"%s\"}", role, password);
        securityToken = given()
                .contentType("application/json")
                .body(json)
                //.when().post("/api/login")
                .when().post("/login")
                .then()
                .extract().path("token");
        //System.out.println("TOKEN ---> " + securityToken);
    }

    private void logOut() {
        securityToken = null;
    }

    @Test
    public void serverIsRunning() {
        given().when().get("/info").then().statusCode(200);
    }

    // added 1 Course.
    @Test
    public void testGetCourses() {
        System.out.println("Testing get all Courses");
        given()
                .contentType("application/json")
                .when()
                .get("/course/all").then()
                .statusCode(200)
                .assertThat().body("size()", is(1));
    }

    @Test
    public void testAddCourse() {
        String courseName = "TypeScript";
        String description = "Learn to code in TypeScript";
        login("admin", "test");
        String json = String.format("{courseName: \"%s\", description: \"%s\"}", courseName, description);
        given().contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(json)
                .when()
                .post("/course/add")
                .then()
                .statusCode(200);
    }

    @Test
    public void testGetCourseByName() {
        System.out.println("Testing get Course by name");
        given()
                .contentType("application/json")
                .when()
                .get("/course/JavaScript").then()
                .statusCode(200)
                .assertThat().body("description", equalTo("Learn to code in JavaScript"));
    }

    @Test
    public void testAddClassToCourse() {
        String courseName = "JavaScript";
        int semester = 1;
        int numberOfStudents = 20;
        login("admin", "test");
        String json = String.format("{semester: \"%s\", numberOfStudents: \"%s\", courseName: \"%s\"}", semester, numberOfStudents, courseName);
        given().contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(json)
                .when()
                .post("/course/addTo/class")
                .then()
                .statusCode(200);
    }

    // TODO
    @Disabled
    @Test
    public void testUpdateCourse() {
        String courseName = "Testing";
        String description = "Bla bla";
        login("admin", "test");
        String json = String.format("{courseName: \"%s\", description: \"%s\"}", courseName, description);
        given().contentType("application/json")
                .accept(ContentType.JSON)
                .header("x-access-token", securityToken)
                .body(json)
                .when()
                .put("/course/update/JavaScript")
                .then()
                .statusCode(200);
    }

    // TODO
    @Disabled
    @Test
    public void testGetClassBySemester() {
        System.out.println("Testing get class by semester");
        given()
                .contentType("application/json")
                .when()
                .get("/course/getClass/1").then()
                .statusCode(200)
                .assertThat().body("numberOfStudents", equalTo("2"));
    }
}
