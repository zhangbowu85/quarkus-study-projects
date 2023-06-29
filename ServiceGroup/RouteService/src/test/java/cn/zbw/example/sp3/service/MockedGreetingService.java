package cn.zbw.example.sp3.service;

import cn.zbw.example.sp3.services.GreetingService;
import io.quarkus.test.Mock;

@Mock
public class MockedGreetingService extends GreetingService {

    @Override
    public String greetingMessage(int age) {
        return "Hello, Mock Service";
    }
}
