package cn.zbw.example.sp3.route;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;


@QuarkusTest
public class ResourceRouteTest {

    @Test
    public void testRourseService() {
        given().when().get("/rest/hello?name=zhangsan").then()
                .statusCode(200)
                .body(is("Hello, zhangsan"));
    }
}
