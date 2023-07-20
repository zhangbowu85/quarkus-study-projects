package cn.zbw.example.sp2.entity;

import io.quarkus.hibernate.reactive.panache.PanacheEntity;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "role")
public class Role extends PanacheEntity implements Serializable {

    private  static final long serialVersionUID = 1L;

    public String roleName;
    public String roleDescription;

    @ManyToMany(mappedBy = "roles", cascade = CascadeType.MERGE)
    public Set<User> users;
}
