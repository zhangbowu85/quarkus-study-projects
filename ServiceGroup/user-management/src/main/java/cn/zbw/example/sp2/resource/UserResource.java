package cn.zbw.example.sp2.resource;

import cn.zbw.example.sp2.entity.User;
import cn.zbw.example.sp2.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.quarkus.hibernate.reactive.panache.Panache;
import io.smallrye.mutiny.CompositeException;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.jboss.logging.Logger;
import org.jboss.resteasy.reactive.RestPath;

import java.util.List;

@Path("/user")
public class UserResource {

    @Inject
    UserService userService;

    private static final Logger LOGGER = Logger.getLogger(UserResource.class.getName());


    @GET
    public Uni<List<User>> listAll() {
        return Panache.withTransaction(() -> {
            Uni<List<User>> users = User.listAll();
            return users.onItem().invoke(it -> {
               it.forEach(user -> user.roles = null);
            });
        });
    }

    @GET
    @Path("{id}")
    public Uni<User> findById(@RestPath Long id) {
        return Panache.withTransaction(() -> {
            return userService.findById(id).onItem().invoke(it -> it.roles = null);
        });
    }

    @GET
    @Path("/presenters")
    public Uni<List<User>> findPresenter() {
        return User.findPresenters();
    }

    @POST
    public Uni<Response> create(User user) {
        if (user == null && user.name == null) {
            throw new WebApplicationException("User name must be set", 422);
        }
        return Panache.withTransaction(user::persist).
                replaceWith(Response.ok(user).status(Response.Status.CREATED)::build);
    }

    @DELETE
    @Path("{id}")
    public Uni<Response> delete(@RestPath Long id) {
        return Panache.withTransaction( () -> User.deleteById(id))
                .map(deleted -> deleted ?
                        Response.ok().status(Response.Status.NO_CONTENT).build() :
                        Response.ok().status(Response.Status.NOT_FOUND).build());
    }

    /**
     * Create a HTTP response from an exception.
     *
     * Response Example:
     *
     * <pre>
     * HTTP/1.1 422 Unprocessable Entity
     * Content-Length: 111
     * Content-Type: application/json
     *
     * {
     *     "code": 422,
     *     "error": "Fruit name was not set on request.",
     *     "exceptionType": "jakarta.ws.rs.WebApplicationException"
     * }
     * </pre>
     */
    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Inject
        ObjectMapper objectMapper;

        @Override
        public Response toResponse(Exception exception) {
            LOGGER.error("Failed to handle request", exception);

            Throwable throwable = exception;

            int code = 500;
            if (throwable instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }

            // This is a Mutiny exception and it happens, for example, when we try to insert a new
            // fruit but the name is already in the database
            if (throwable instanceof CompositeException) {
                throwable = ((CompositeException) throwable).getCause();
            }

            ObjectNode exceptionJson = objectMapper.createObjectNode();
            exceptionJson.put("exceptionType", throwable.getClass().getName());
            exceptionJson.put("code", code);

            if (exception.getMessage() != null) {
                exceptionJson.put("error", throwable.getMessage());
            }

            return Response.status(code)
                    .entity(exceptionJson)
                    .build();
        }

    }
}
