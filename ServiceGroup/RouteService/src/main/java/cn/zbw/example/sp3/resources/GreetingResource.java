package cn.zbw.example.sp3.resources;


import cn.zbw.example.sp3.services.GreetingService;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Path("/test")
public class GreetingResource {

    @Inject
    GreetingService greetingService;

    @Path("/greeting")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String greeting(@QueryParam("age") int age) {
        return greetingService.greetingMessage(age);
    }

}
