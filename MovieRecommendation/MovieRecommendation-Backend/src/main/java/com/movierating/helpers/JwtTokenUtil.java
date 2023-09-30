   package com.movierating.helpers;
    import com.auth0.jwt.JWT;
    import com.auth0.jwt.JWTVerifier;
    import com.auth0.jwt.algorithms.Algorithm;
    import com.auth0.jwt.interfaces.DecodedJWT;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.stereotype.Service;
    
    @Service
    public class JwtTokenUtil {
      private final Algorithm algo;
    
      public JwtTokenUtil(@Value("${jwt.secret}") String secret) {
        algo = Algorithm.HMAC256(secret);
      }
    
      public String createToken(int id) {
        String token = JWT.create().withIssuer("arcanecat.com").withClaim("user_id", id).sign(algo);
        return token;
      }
    
      public String decodeToken(String token) {
        DecodedJWT decodedJwt;
        JWTVerifier verifier = JWT.require(algo).withIssuer("arcanecat.com").build();
        decodedJwt = verifier.verify(token);
        String value = decodedJwt.getClaim("user_id").toString();
        if (value == null) {
          throw new IllegalArgumentException();
        }
       return value;
     }
   }
