package rest;

import DTO.TeacherDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import errorhandling.DuplicateException;
import errorhandling.NotFoundException;
import facades.TeacherFacade;
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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;
import utils.EMF_Creator;

@Path("teacher")
public class TeacherResource {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private static final EntityManagerFactory EMF = EMF_Creator.createEntityManagerFactory();
    private static final TeacherFacade TF = TeacherFacade.getTeacherFacade(EMF);

    @Context
    private UriInfo context;

    @Context
    SecurityContext securityContext;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/teachers")
    @RolesAllowed("admin")
    public String allTeachers() {
        List<TeacherDTO> teacherListDTO = TF.getAllTeachers();
        return GSON.toJson(teacherListDTO);
    }

    @POST
    @Produces({MediaType.APPLICATION_JSON})
    @Consumes({MediaType.APPLICATION_JSON})
    @Path("/add")
    @RolesAllowed("admin")
    public String addTeacher(String teacherDTO) throws DuplicateException, NotFoundException {
        TeacherDTO tDTO = GSON.fromJson(teacherDTO, TeacherDTO.class);
        TF.addTeacher(tDTO);
        return GSON.toJson(tDTO);
    }

//    @PUT
//    @Path("/update/teacher")
//    @Produces({MediaType.APPLICATION_JSON})
//    @Consumes({MediaType.APPLICATION_JSON})
//    @RolesAllowed("admin")
//    public String addClassToTeacher(int teacherId, int classEntity) throws NotFoundException {
//        TeacherDTO tNew = TF.addClassToTeacher(teacherId, classEntity);
//        return GSON.toJson(tNew);
//    }
}
