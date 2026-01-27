package com.store.springboot_ecommerce.service;

//import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.store.springboot_ecommerce.model.User;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import java.security.Key;

@Component
public class JwtService {


    private final String secretKey = "x0v9aF8+Y3k2t7h1F9J5l+ZqR4pD7tVwL6h9sXbQ2nM="; 

    private long expireTime = 3600000 ;

    private Key key ;

    @PostConstruct
    public void init(){
        this.key = Keys.hmacShaKeyFor(secretKey.getBytes());
    }


    public String generateToken(User user){
    return Jwts.builder()
        .setSubject(user.getEmail())
        .claim("role" , user.getRole())
        .setIssuedAt(new Date())
        .setExpiration(new Date(System.currentTimeMillis()+expireTime))
        .signWith(key ,SignatureAlgorithm.HS256)
        .compact();

    }


    public String extractEmail(String token) {
    
        return Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
    }


    public boolean isTokenValid(String token, User user) {
    
        String email = extractEmail((token));
        return email.equals(user.getEmail()) && !isTokenExpired(token); 
    }


    private boolean isTokenExpired(String token) {
        Date expiration = Jwts.parserBuilder()
        .setSigningKey(key)
        .build()
        .parseClaimsJws(token)
        .getBody()
        .getExpiration();

        return expiration.before(new Date());
    }

    
}