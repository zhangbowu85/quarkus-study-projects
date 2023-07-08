package cn.zbw.example.sp2.service;

import cn.zbw.example.sp2.entity.User;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import org.redisson.api.RedissonClient;
import org.redisson.codec.JsonJacksonCodec;

public class UserService {

    @Inject
    RedissonClient redisson;

    @Inject
    JsonJacksonCodec code;

    public User findById(long userId) {


        try {
            User user = JsonB.redisson.getMap("user")
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        User user = users.get(userId);

        return null;
    }
}
