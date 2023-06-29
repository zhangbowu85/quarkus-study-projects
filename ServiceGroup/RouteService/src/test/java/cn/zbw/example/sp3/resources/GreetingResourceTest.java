package cn.zbw.example.sp3.resources;

import cn.zbw.example.sp3.services.GreetingService;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.junit.mockito.InjectMock;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.when;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

@QuarkusTest
class GreetingResourceTest {

    @InjectMock
    GreetingService greetingService;

    @BeforeEach
    public void prepareMocks() {
        when(greetingService.greetingMessage(18))
                .thenReturn((String)"Aloha from Mockito");
    }

    @Test
    public void testHelloEndpoint() {
        given().when().get("/test/greeting?age=18")
            .then()
                .statusCode(200)
                .body(is("Aloha from Mockito"));
    }

    @Test
    public void testHelloEndpointForYounger() {
        given().when().get("/test/greeting?age=15")
                .then()
                .statusCode(204);
    }
}
