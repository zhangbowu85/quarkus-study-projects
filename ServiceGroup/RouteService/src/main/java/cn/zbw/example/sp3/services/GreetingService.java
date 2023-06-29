package cn.zbw.example.sp3.services;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.validation.constraints.Min;

@ApplicationScoped
public class GreetingService {

    public String greetingMessage(@Min(value = 16) int age) {
        if (age < 19) {
            return "Hey boys and girls";
        } else {
            return "Hey ladies and gentlemen";
        }
    }
}
