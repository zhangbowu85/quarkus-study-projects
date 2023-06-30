package cn.zbw.example.sp3.service;

import io.quarkus.test.Mock;
import io.quarkus.test.junit.QuarkusTest;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

/**
 * This test is only show the Mock usage, meaningless for real-world
 * The usage of Mocked object is to be injected to a tested class to make the tested class's dependency available
 */
@QuarkusTest
public class MockedObjectTest{

    @ApplicationScoped
    static class RealObject {
        String sayHello() {
            return "Hello From RealObject";
        }
    }

    @Mock
    @ApplicationScoped
    static class MockedObject extends RealObject {
        @Override
        String sayHello() {
            return "Hello From MockedObject";
        }
    }

    @Inject
    RealObject object;

    @Test
    public void  testSayHello() {
        assert(object.sayHello().equals("Hello From MockedObject"));
    }

}
