package cn.zbw.example.sp4.resource;

import cn.zbw.example.sp4.entity.Token;
import cn.zbw.example.sp4.service.TokenService;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;

import java.time.Instant;
import java.util.UUID;

@Path("/token")
public class TokenResource {

    private final int timeDuration = 24 * 60 * 60 * 1000;

    @Inject
    TokenService tokenService;

    @GET
    @Path("/new")
    @Produces("application/json")
    public Token newToken(@QueryParam("userName") String userName) {
        String token = UUID.randomUUID().toString();
        Token tokenObj = new Token(userName, token,
                Instant.now().toEpochMilli(), Instant.now().plusMillis(timeDuration).toEpochMilli());
        tokenService.set(userName, tokenObj);
        return tokenObj;
    }

    @GET
    @Path("/{userName}")
    @Produces("application/json")
    public Token getToken(@PathParam("userName") String userName) {
        return tokenService.get(userName);
    }

}
