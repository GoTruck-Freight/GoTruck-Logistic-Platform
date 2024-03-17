package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JwtService {

    private final Key SECRET_KEY = getSigning();

    private Key getSigning() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

    @Override
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public String generateToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", ((User) userDetails).getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) // 1 day
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    public String generateResetToken(User user) {
        return Jwts.builder()
                .subject(Long.toString(user.getId()))
                .claim("email", user.getEmail())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public Boolean validationToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        final Long userId = extractUserId(token);
        return (username.equals(userDetails.getUsername()) && userId.equals(((User) userDetails).getId()) && !isTokenExpired(token));
    }


    public Long extractUserId(String token) {
        return Long.parseLong(extractClaims(token, Claims::getSubject));
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        try {
            return Jwts
                    .parser()
                    .verifyWith((SecretKey) SECRET_KEY)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (SignatureException e) {
            throw new RuntimeException("Invalid token");
        }
    }

    public Date getExpirationDateFromToken(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }
}