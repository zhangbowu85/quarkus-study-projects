package cn.zbw.example.sp3.route;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.QueryParam;

import java.util.Locale;

@Path("/rest")
public class RestResources {


    @GET
    @Path("/hello")
    public String hello(@QueryParam("name") String name) {
        System.out.println("I am /Rest/hello service");
        return "Hello, " + name;
    }
}
