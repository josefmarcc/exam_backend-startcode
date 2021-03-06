package rest;

import DTO.ClassEntityDTO;
import DTO.CourseDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities.Course;
import errorhandling.DuplicateException;
import errorhandling.NotFoundException;
import facades.CourseFacade;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

@Path("course")
public class CourseResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final CourseFacade CF = CourseFacade.getCourseFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("all")
    public String allCourses() {
        List<CourseDTO> courseList = CF.getAllCourses();
        return GSON.toJson(courseList);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/classes")
    @RolesAllowed("admin")
    public String allClasses() {
        List<ClassEntityDTO> classEntityList = CF.getAllClasses();
        return GSON.toJson(classEntityList);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("add")
    @RolesAllowed("admin")
    public String addCourse(String course) throws DuplicateException {
        String thisuser = securityContext.getUserPrincipal().getName();
        Course c = GSON.fromJson(course, Course.class);
        CF.addCourse(c.getCourseName(), c.getDescription());

        return "Course: " + c.getCourseName() + " Added by " + thisuser;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{courseName}")
    public String getCourseByName(@PathParam("courseName") String courseName) throws NotFoundException {
        CourseDTO course = CF.getCourseByName(courseName);
        return GSON.toJson(course);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/getClass/{semester}")
    public String getClassBySemester(@PathParam("semester") int semester) throws NotFoundException {
        List<ClassEntityDTO> classEntityDTO = CF.getClassBySemester(semester);
        return GSON.toJson(classEntityDTO);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/addTo/class")
    @RolesAllowed("admin")
    public String addClassToCourse(String classEntity) throws NotFoundException {
        String thisuser = securityContext.getUserPrincipal().getName();
        ClassEntityDTO classEntityDTO = GSON.fromJson(classEntity, ClassEntityDTO.class);
        CF.addClassToCourse(classEntityDTO.getSemester(), classEntityDTO.getNumberOfStudents(), classEntityDTO.getCourseName());

        return "Added " + classEntityDTO.getCourseName() + " to " + classEntityDTO.getSemester() + " By " + thisuser;
    }

    @PUT
    @Path("/update/class")
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public String updateCourse(String course) throws NotFoundException {
        CourseDTO cDTO = GSON.fromJson(course, CourseDTO.class);
        CourseDTO cNew = CF.editCourse(cDTO);
        return GSON.toJson(cNew);
    }

    @DELETE
    @Path("/delete/{courseName}")
    @Produces({MediaType.APPLICATION_JSON})
    @RolesAllowed("admin")
    public String deleteCourse(@PathParam("courseName") String courseName) throws NotFoundException {
        CF.deleteCourse(courseName);
        return "Deleted: " + courseName;
    }

}
