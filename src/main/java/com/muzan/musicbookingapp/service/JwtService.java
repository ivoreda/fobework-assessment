package com.muzan.musicbookingapp.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
public class JwtService {
    // can keep here for now
    private static final String JWT_SECRET = "bddef64c6b57fe4fd348c1f0b9c31397febbaebb1b1e3460bc91ffedf672d5c7d8146a0a49f70dfaba7a9d885668e92e8011459b6a7b29278de5362378c2869faf98153e5e090850eedb598f4912070453d8e32454851f1c7076ac7e1ba29685119de621ae247300844812021ed813a44e727464ebdd5baa6fb1c43dc563b1fe1fe9bea5a56c82acfa262433b07f635b17aa799ace7d8a26cfae127677c9432d5739edd281030a639de43515851accc49df3dda43176485516d227b1cc8de95192675e1281a164fea7a0a3947a1c0c7b9c4f5cf5e2712dfc0e4534fd4e4eef0f6c1b4f2a7d5366f00def9ffc6ede0dbb06db8338b3e16fb588b8f7a0e2df42eb";

    public String generateToken(UserDetails userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);

    }
}
