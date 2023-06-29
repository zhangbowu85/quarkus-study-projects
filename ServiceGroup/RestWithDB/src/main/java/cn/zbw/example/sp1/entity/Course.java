package cn.zbw.example.sp1.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;


/**
 * Example JPA entity defined as a Panache Entity.
 * An ID field of Long type is provided, if you want to define your own ID field extends <code>PanacheEntityBase</code> instead.
 *
 * This uses the active record pattern, you can also use the repository pattern instead:
 * .
 *
 * Usage (more example on the documentation)
 *
 * {@code
 *     public void doSomething() {
 *         Course entity1 = new Course();
 *         entity1.field = "field-1";
 *         entity1.persist();
 *
 *         List<Course> entities = Course.listAll();
 *     }
 * }
 */
@Entity
@Table(name = "course")
public class Course extends PanacheEntity {
    private String name;
    private String description;

    public Course() {

    }

    public Course(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
