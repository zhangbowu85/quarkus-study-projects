package cn.zbw.example.sp2.service;

import cn.zbw.example.sp2.entity.User;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.redisson.Redisson;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.util.Optional;

@ApplicationScoped
public class UserService {

    final String prefixUserKey = "user-";

    static Config config = new Config();
    static RedissonClient redisson;
    static {
        config.useSingleServer()
                .setAddress("redis://127.0.0.1:6379");
        redisson = Redisson.create(config);
    }



    public Uni<User> findById(long userId) {
        System.out.println("Query user " + userId);
        RBucket<User> userBucket = redisson.getBucket(prefixUserKey + userId);
        User user = userBucket.get();

        if (user != null) {
            return Uni.createFrom().item(user);
        }
        System.out.println("No cache found, to query database.");
        Uni<User> uniUser = User.findById(userId);
        return uniUser.invoke(it ->  {
            System.out.println("To cache user" + it);
            userBucket.set(it);
        });

    }
}
