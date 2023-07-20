package cn.zbw.example.sp2.resource;

import cn.zbw.example.sp2.entity.Role;
import cn.zbw.example.sp2.service.RoleService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.util.List;

@Path("/role")
public class RoleResource {

    @Inject
    RoleService roleService;

    @GET
    public Uni<List<Role>> getAllRoles() {
        return roleService.getAllRoles();
    }

    @GET
    @Path("/{id}")
    public Uni<Role> getRoleById(@PathParam("id") Long id) {
        return roleService.getRoleById(id);
    }

    @POST
    public Uni<Role> createRole(Role role) {
        return roleService.createRole(role);
    }

    @PUT
    @Path("/{id}")
    public Uni<Role> updateRole(@PathParam("id") Long id, Role role) {
        return roleService.updateRole(id, role);
    }

    @DELETE
    @Path("/{id}")
    public Uni<Void> deleteRole(@PathParam("id") Long id) {
        roleService.deleteRole(id);
        return Uni.createFrom().nullItem();
    }
}
