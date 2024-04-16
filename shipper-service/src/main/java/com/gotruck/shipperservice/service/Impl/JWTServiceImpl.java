package com.gotruck.shipperservice.service.Impl;

import com.gotruck.shipperservice.model.User;
import com.gotruck.shipperservice.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JwtService {

    private final SecretKey secretKey;
    @Autowired
    public JWTServiceImpl(@Value("${security.jwt.secret-key}") String secretKey) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    @Override
    public String extractUserName(String token) {
        return extractClaims(token, Claims::getSubject);
    }

    @Override
    public String generateAccessToken(UserDetails userDetails) {
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", ((User) userDetails).getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5)) // 5 min
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        if (isAuthorized(userDetails)) {
            throw new IllegalArgumentException("User is not authorized to generate refresh token");
        }
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", ((User) userDetails).getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(secretKey)
                .compact();
    }

    @Override
    public String generateResetToken(UserDetails userDetails) {
        if (isAuthorized(userDetails)) {
            throw new IllegalArgumentException("User is not authorized to generate refresh token");
        }
        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("userId", ((User) userDetails).getId())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 3))
                .signWith(secretKey)
                .compact();
    }

    private boolean isAuthorized(UserDetails userDetails) {
        return false;
    }

    @Override
    public Boolean validationToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        final Long userId = extractUserId(token);
        return (username.equals(userDetails.getUsername()) && userId.equals(((User) userDetails).getId()) && !isTokenExpired(token));
    }

    public Long extractUserId(String token) {
        Claims claims = extractAllClaims(token);
        return claims.get("userId", Long.class);
    }

    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        try {
            return Jwts
                    .parser()
                    .verifyWith(secretKey)
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