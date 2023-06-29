package cn.zbw.example.sp3.route;

import io.quarkus.vertx.http.runtime.filters.Filters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;

@ApplicationScoped
public class RouterFilter {

    public void authFilter(@Observes Filters filters) {
        filters.register(
                rc -> {
                    System.out.println(String.format("Filter - read authorization string: %s, %s",
                            rc.request().headers().get("Authorization"),
                            rc.request().path()));
                    rc.response().putHeader("V-Header", "Header added by VertX filter.");
                    rc.next();
                }
        , 5);
    }
}
