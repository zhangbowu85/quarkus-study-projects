package cn.zbw.example.sp2.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import io.quarkus.hibernate.reactive.panache.PanacheEntityBase;
import io.smallrye.mutiny.Uni;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Cacheable
@Table(name = "users")
public class User extends PanacheEntity implements Serializable {

    private  static final long serialVersionUID = 1L;

    @Column(length = 50, unique = true)
    public String name;
    public String type;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role")
    public Set<Role> roles;

    public static Uni<User> findByName(String name) {
        return PanacheEntityBase.find("name", name).firstResult();
    }

    public static Uni<List<User>> findPresenters() {
        return PanacheEntityBase.list("status", "presenter");
    }
}
