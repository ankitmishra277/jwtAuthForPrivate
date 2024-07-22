package com.example.jwtprivatekey;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtUtil {

    @Autowired
    private KeyGeneratorUtil keyGeneratorUtil;

    private PrivateKey privateKeyString = null;
    @Autowired
    private PublicKey publicKeyString;

    private static final ObjectMapper objectMapper = new ObjectMapper();


    public String generateToken(String subject,String payload) {
        Map<String, Object> map =new HashMap<>();
        try {
            map = objectMapper.readValue(payload, new TypeReference<Map<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return Jwts.builder()
                .setHeader(new HashMap<>())
                .addClaims(map)
                .signWith(privateKeyString)
                .compact();
    }

    public Claims getClaimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(publicKeyString)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(publicKeyString)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (Exception e) {
            // Handle invalid JWT or JWT expired exception
            return false;
        }
    }
    public String extractUsername(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(publicKeyString)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            return claims.getSubject(); // Subject (username) is typically stored in the JWT claims
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract username from JWT token", e);
        }
    }

}
