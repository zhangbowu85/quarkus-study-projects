package cn.zbw.example.sp3.service;

import cn.zbw.example.sp3.services.GreetingService;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import jakarta.validation.ConstraintViolationException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class GreetingServiceTest {
    @Inject
    GreetingService greetingService;

    @Test
    public  void testGreetingServiceForYoungers() {
        Assertions.assertThatExceptionOfType(ConstraintViolationException.class)
                .isThrownBy(() -> greetingService.greetingMessage(15));
    }

    @Test
    public void testGreetingServiceForTeenagers() {
        String message = greetingService.greetingMessage(18);
        Assertions.assertThat(message).isEqualTo("Hey boys and girls");
    }

    @Test
    public void testGreetingServiceForAdult() {
        String message = greetingService.greetingMessage(28);
        Assertions.assertThat(message).isEqualTo("Hey ladies and gentlemen");
    }
}
