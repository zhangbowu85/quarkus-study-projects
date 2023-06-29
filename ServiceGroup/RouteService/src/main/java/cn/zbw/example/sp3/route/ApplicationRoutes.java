package cn.zbw.example.sp3.route;

import io.quarkus.vertx.web.Param;
import io.quarkus.vertx.web.Route;
import io.quarkus.vertx.web.RoutingExchange;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class ApplicationRoutes {
    public void routes(@Observes Router router) {
        router.get("/helloworld")
                .handler(rc -> rc.response().end("Thank you for saying helloworld!"));
    }

    @Route(methods = Route.HttpMethod.GET)
    void  hello(RoutingContext rc) {
        rc.response().end("hello");
    }

    @Route(path = "/world")
    String helloWorld() {
        return "Hello world!";
    }

    @Route(path = "/greetings", methods = Route.HttpMethod.GET)
    void greetingsQueryParam(RoutingExchange ex) {
        ex.ok("greeting - hello" + ex.getParam("name").orElse("world"));
    }

    @Route(path = "/greetings/:name", methods = Route.HttpMethod.GET)
    void greetingsPathParam(@Param String name, RoutingExchange ex) {
        ex.ok("greeting, hello " + name);
    }
}
