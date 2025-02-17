package com.bookstar.bookingservice.configuration.security;

import com.bookstar.bookingservice.configuration.security.dto.Token;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;

@Component
@Log4j2
public class JWTUtil {

    @Value("${jwt.jwt_secret}")
    private String secret;

    private final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    static ObjectMapper mapper = new ObjectMapper();

    public static Token decodeToken(String token){
        try {
            String[] chunks = token.split("\\.");

            Base64.Decoder decoder = Base64.getUrlDecoder();

            String header = new String(decoder.decode(chunks[0]));
            String payload = new String(decoder.decode(chunks[1]));


            return mapper.readValue(payload, Token.class);
        } catch (JsonProcessingException e) {
            log.error(String.format("Token could not be decoded: %s", e.getMessage()));
            return null;
        }
    }

    public String generateToken(String subject) {
        Key signingKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .subject(subject)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(signingKey)
                .compact();
    }

    public static String generateToken(String subject, String issuer, Long expiration, String secret) {
        Key signingKey = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .subject(subject)
                .issuer(issuer)
                .expiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(signingKey)
                .compact();
    }
}













