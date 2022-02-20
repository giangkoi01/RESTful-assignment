package controller;

import entity.Student;
import service.StudentService;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/students")
public class StudentController {

    StudentService studentService = new StudentService();

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getListStudent() {
        return studentService.getListStudent();
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> getStudent(@QueryParam("name") String name, @QueryParam("hometown") String hometown, @QueryParam("gender") String gender,
                                        @QueryParam("classname") String classname, @QueryParam("major") String major,
                                        @QueryParam("mark") List<Float> mark, @QueryParam("birthday") List<Integer> birthday) {
        return studentService.search(name, hometown, gender, classname, major, mark, birthday);
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String addNewStudent(@Valid Student student) {
        return studentService.insert(student) ? "Thêm mới thành công" : "Thêm mới thất bại";
    }

    @PUT
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public String updateStudent(Student student){
        return studentService.update(student) ? "update thành công" : "update thất bại";
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public String removeStudent(@PathParam("id") int id) {
        return studentService.removeStudent(id) ? "Xóa thành công" : "Xóa thất bại";
    }

    @GET
    @Path("/birthday")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Student> hpBirthday(){
        return studentService.birthday();
    }

}
