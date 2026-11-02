package com.ecommerce.security;

import java.security.Key;
import java.util.*;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	@Value("${app.jwt.secret}")
	private String jwtSecret;
	@Value("${app.jwt.expiration-ms}")
	private long jwtExpirationMs;
	
	//to convert our string type secret key to hmac sha key obj
	private Key getSigningKey() {
		return Keys.hmacShaKeyFor(jwtSecret.getBytes());
	}
	
	public String generateToken(String email,String role,int userId) {
		Map<String,Object> claims=new HashMap<>();
		claims.put("role", role);
		claims.put("userId", userId);
		
		return Jwts.builder().
				setClaims(claims).
				setSubject(email).
				setIssuedAt(new Date()).
				setExpiration(new Date(System.currentTimeMillis()+jwtExpirationMs)).
				signWith(getSigningKey(),SignatureAlgorithm.HS256).
				compact();
	}
	
	public String extractEmail(String token) {
		return getClaims(token).getSubject();
	}
	
	public Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
	
	public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token);
            return true;
        }
        catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }
	
	
}
