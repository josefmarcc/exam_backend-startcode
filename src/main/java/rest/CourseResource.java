package rest;

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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.Produces;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("add")
    @RolesAllowed("admin")
    public String addCourse(String course) throws DuplicateException {
        Course c = GSON.fromJson(course, Course.class);
        CF.addCourse(c.getCourseName(), c.getDescription());

        return "Course: " + c.getCourseName() + " Added";
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/{courseName}")
    public String getCourseByName(@PathParam("courseName") String courseName) throws NotFoundException {
        CourseDTO course = CF.getCourseByName(courseName);
        return GSON.toJson(course);
    }

}
