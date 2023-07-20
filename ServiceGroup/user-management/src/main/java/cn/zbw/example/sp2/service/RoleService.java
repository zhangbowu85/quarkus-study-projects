package cn.zbw.example.sp2.service;

import cn.zbw.example.sp2.entity.Role;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.quarkus.hibernate.reactive.panache.common.runtime.ReactiveTransactional;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;

import java.time.Duration;
import java.util.List;


@ApplicationScoped
public class RoleService {

    public Uni<Role> createRole(Role role) {
        return Panache.withTransaction(role::persist)
                .replaceWith(Uni.createFrom().item(role));
    }

    public Uni<Role> updateRole(Long id, Role role) {
        return Panache.withTransaction(() -> {
            Uni<Role> existingRole = null;
            existingRole = Role.findById(id);
            existingRole.onItem().invoke(it -> {
                it.roleName = role.roleName;
                it.roleDescription = role.roleDescription;
                it.persist();
            }).ifNoItem().after(Duration.ofMillis(20))
                    .failWith(new Throwable("No role found with id" + id));
            return existingRole;

        });
    }

    public void deleteRole(Long id) {
        Panache.withTransaction(() -> {
            return  Role.findById(id).onItem().invoke(it -> {
                        it.delete();
                    }).ifNoItem().after(Duration.ofMillis(20))
                    .failWith(new Throwable(String.format("No role with %d found.", id)));
        });
    }

    public Uni<List<Role>> getAllRoles() {
        return Panache.withTransaction(() -> {
            Uni<List<Role>> roles = Role.listAll();
            return roles.onItem().invoke(it ->
                        it.forEach(eachRole -> {
                            eachRole.users = null;
                        }));
        });

    }

    public Uni<Role> getRoleById(Long id) {
        return Panache.withTransaction(() -> {
            Uni<Role> role = Role.findById(id);
            return role.onItem().invoke(it -> it.users = null);
        });
    }
}

