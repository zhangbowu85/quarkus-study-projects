package cn.zbw.example.sp2.service;

import jakarta.enterprise.context.ApplicationScoped;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import java.io.IOException;

@ApplicationScoped
public class RedissonPool {

    Config config;
    public RedissonPool() throws IOException {
        config = Config
                .fromYAML(RedissonPool.class.getClassLoader().getResource("redisson.yaml"));
    }

    public RedissonClient getClient() {
        return Redisson.create(config);
    }

    public void releaseClient(RedissonClient client) {
        client.shutdown();
    }

}
