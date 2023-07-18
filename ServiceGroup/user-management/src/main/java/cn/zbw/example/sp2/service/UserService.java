package cn.zbw.example.sp2.service;

import cn.zbw.example.sp2.entity.User;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;

@ApplicationScoped
public class UserService {

    final String prefixUserKey = "user-";

    @Inject
    RedissonPool redissonPool;


    public Uni<User> findById(long userId) {
        System.out.println("Query user " + userId);
        RedissonClient redisson = redissonPool.getClient();
        RBucket<User> userBucket = redisson.getBucket(prefixUserKey + userId);
        User user = userBucket.get();

        try {
            if (user != null) {
                return Uni.createFrom().item(user);
            }
            System.out.println("No cache found, to query database.");
            Uni<User> uniUser = User.findById(userId);
            return uniUser.invoke(it -> {
                System.out.println("To cache user" + it);
                userBucket.set(it);
            });
        } finally {
            redissonPool.releaseClient(redisson);
        }

    }
}
