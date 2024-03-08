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

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JWTServiceImpl implements JwtService {

    private final Key SECRET_KEY = getSigning();

//    signing key
    private Key getSigning() {
        return Keys.secretKeyFor(SignatureAlgorithm.HS256);
    }

//    extracting userName
    @Override
    public String extractUserName(String token) {
        return extractClaims(token,Claims::getSubject);
    }

//    generating token
    @Override
    public String generateToken(UserDetails userDetails) {
        return   Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24)) //1 gün
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

//    generating reset token
    public String generateResetToken(User user) {
        return   Jwts.builder()
                .setSubject(Long.toString(user.getId()) )// Ve ya user.getUserName() kimi uygun bir metodu kullanın
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 24))
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

//    extracting claims
    public <T> T extractClaims(String token, Function<Claims, T> claimsResolver){
        final Claims claims=extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token){
        try {
            return Jwts.parser()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (SignatureException e) {
            throw new RuntimeException("Token uyğunsuzdur");
        }
    }


//    checking expiration
    public Date getExpirationDateFromToken(String token){
        return extractClaims(token,Claims::getExpiration);
    }

    private Boolean isTokenExpired(String token){
        final Date expiration = getExpirationDateFromToken(token);
        return expiration.before(new Date());
    }

    @Override
    public Boolean validationToken(String token, UserDetails userDetails) {
        final String username = extractUserName(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));

    }
}
