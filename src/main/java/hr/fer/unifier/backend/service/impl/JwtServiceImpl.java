package hr.fer.unifier.backend.service.impl;

import hr.fer.unifier.backend.UnifierProperties;
import hr.fer.unifier.backend.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UnifierProperties unifierProperties;

    @Override
    public String extractUsername(String jwt) {
        return extractAuthClaims(jwt, Claims::getSubject);
    }

    @Override
    public String generateAuthToken(UserDetails userDetails) {
        return generateAuthToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateAuthToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                .signWith(getAuthSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenValid(String jwt, UserDetails userDetails) {
        final String username = extractUsername(jwt);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(jwt);

    }

    @Override
    public Long extractUserId(String jwt) {
        Claims claims = extractAuthClaims(jwt);
        return claims.get("id", Long.class);
    }

    @Override
    public String generateRefreshToken(UserDetails userDetails) {
        return generateRefreshToken(new HashMap<>(), userDetails);
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24))
                .signWith(getRefreshSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private boolean isTokenExpired(String jwt) {
        return extractExpiration(jwt).before(new Date());
    }

    private Date extractExpiration(String jwt) {
        return extractAuthClaims(jwt, Claims::getExpiration);
    }


    public <T> T extractAuthClaims(String jwt, Function<Claims, T> claimsResolver){
        final Claims claims = extractAuthClaims(jwt);
        return claimsResolver.apply(claims);
    }

    private Claims extractAuthClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getAuthSigningKey())
                .build()
                .parseClaimsJws(jwt)
                .getBody();
    }

    private Key getAuthSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(unifierProperties.getAuthSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }

    private Key getRefreshSigningKey() {
        final byte[] keyBytes = Decoders.BASE64.decode(unifierProperties.getRefreshSecretKey());
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
