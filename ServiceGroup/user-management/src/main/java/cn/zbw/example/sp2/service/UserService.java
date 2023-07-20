package cn.zbw.example.sp2.service;

import cn.zbw.example.sp2.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.hibernate.reactive.stage.Stage;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

@ApplicationScoped
public class UserService {

    final String prefixUserKey = "user-";

    @Inject
    RedissonPool redissonPool;

    @Inject
    ObjectMapper jsonMapper;


    public Uni<User> findById(long userId)  {
        System.out.println("Query user " + userId);
        RedissonClient redisson = redissonPool.getClient();
        RBucket<String> userBucket = redisson.getBucket(prefixUserKey + userId);
        User user = null;

        try {
            user = jsonMapper.readValue(userBucket.get(), User.class);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        if (user != null) {
            System.out.println("Got user from cache");
            return Uni.createFrom().item(user);
        }
        System.out.println("No cache found, to query database.");

        Uni<User> uniUser = User.findById(userId);
        return uniUser.onItem().invoke(it -> {
            Stage.fetch(it.roles);
            System.out.println("To cache user" + it);
            String jsonString = null;
            try {
                jsonString = jsonMapper.writeValueAsString(it);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }
            userBucket.set(jsonString);
        });

    }
}
