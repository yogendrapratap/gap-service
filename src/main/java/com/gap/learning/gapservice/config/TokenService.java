package com.gap.learning.gapservice.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.stereotype.Service;

@Service
public class TokenService {

    private static final String SECRET_KEY = "Squad7";
    private static final String ISSUER = "gap_service";

   public static void main(String[] args) {

        String token = createJwt("yogendra", "USER");
        System.out.println("Generated JWT token: " + token);

        verifyJwt(token);
    }

    public static String createJwt(String username, String role) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER)
                    .withClaim("username", username)
                    .withClaim("role", role)
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            System.err.println("Error creating JWT: " + exception.getMessage());
            return null;
        }
    }

    public static User verifyJwt(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer(ISSUER)
                    .build();
            DecodedJWT jwt = verifier.verify(token);

            System.out.println("\nJWT Verified Successfully!");
            System.out.println("Username: " + jwt.getClaim("username").asString());
            System.out.println("Role: " + jwt.getClaim("role").asString());
            System.out.println("Issuer: " + jwt.getIssuer());

            User user = new User();
            user.setUsername(jwt.getClaim("username").asString());
            user.setRole(jwt.getClaim("role").asString());

            return user;

        } catch (JWTVerificationException exception) {
            // Invalid signature/claims
            System.err.println("\nError verifying JWT: " + exception.getMessage());
        }
        return null;
    }
}

