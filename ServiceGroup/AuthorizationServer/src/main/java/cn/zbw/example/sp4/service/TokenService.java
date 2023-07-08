package cn.zbw.example.sp4.service;

import cn.zbw.example.sp4.entity.Token;
import io.quarkus.redis.datasource.RedisDataSource;
import io.quarkus.redis.datasource.value.ValueCommands;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class TokenService {

    private final ValueCommands<String, Token> tokens;

    public TokenService(RedisDataSource ds) {
        this.tokens = ds.value(Token.class);
    }

    public Token get(String userName) {
        return tokens.get(userName);
    }

    public void set(String key, Token token) {
        tokens.set(key, token);
    }
}
