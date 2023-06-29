package cn.zbw.example.sp1.service;

import cn.zbw.example.sp1.entity.Course;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.WebApplicationException;

import java.util.List;

/**
 *
 */
@ApplicationScoped
public class CourseService {

    @Inject
    EntityManager entityManager;

    @Transactional
    public void createCourse(Course course) {
        entityManager.persist(course);
    }

    public List<Course> getAll() {
        return Course.listAll();
    }

    public Course getCourse(Integer id) {
        Course course = entityManager.find(Course.class, id);
        if (course == null) {
            throw new WebApplicationException("Course with id " + id + " does not exist.", 404);
        }
        return course;
    }


    @Transactional
    public void deleteCourse(Integer id) {
        Course course = entityManager.getReference(Course.class, id);
        if (course == null) {
            throw new WebApplicationException("Course with id " + id + " is not found.", 404);
        }
        entityManager.remove(course);
    }

}
