package cn.zbw.example.sp1.resource;

import cn.zbw.example.sp1.entity.Course;
import cn.zbw.example.sp1.service.CourseService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriBuilder;

import java.util.List;

@Path("/course")
public class CourseResource {

    @Inject
    CourseService courseService;


    @GET
    @Path("/all")
    public List<Course> list() {
        return courseService.getAll();
    }

    @POST
    @Path("/new")
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(Course course) {
        courseService.createCourse(course);
        return Response.created(
                UriBuilder.fromResource(CourseResource.class)
                        .path(Long.toString(course.id)).build()
        ).entity(course).build();
    }

    @GET
    @Path("{id}")
    public Course queryCourse(Integer id) {
        return courseService.getCourse(id);
    }

    @DELETE
    @Path("{id}")
    public Response delete(Integer id) {
        courseService.deleteCourse(id);
        return Response.status(204).build();
    }


}
