package com.comfy_backend.auth.util;

import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

public class Jwt {

    public String secret = "this-is-top-secret";

    public final int TOKEN_TIMEOUT = 1000 + 60 * 60 * 24 * 7;


    public String createToken(long id){
        Date now = new Date();
        Date exp = new Date(now.getTime() + TOKEN_TIMEOUT);
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return com.auth0.jwt.JWT.create()
                .withSubject(String.valueOf(id))
                .withIssuedAt(now)
                .withExpiresAt(exp)
                .sign(algorithm);
    }
}
