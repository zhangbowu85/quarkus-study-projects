package cn.zbw.example.sp2.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

@Entity
@Cacheable
@Table(name = "users")
public class User extends PanacheEntity {

    @Column(length = 50, unique = true)
    public String name;
    public String type;

    public User() {

    }

    public User(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public static Uni<User> findByName(String name) {
        return PanacheEntityBase.find("name", name).firstResult();
    }

    public static Uni<List<User>> findPresenters() {
        return PanacheEntityBase.list("status", "presenter");
    }
}
