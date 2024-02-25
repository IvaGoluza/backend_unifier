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
import java.util.Map;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final UnifierProperties unifierProperties;

    @Override
    public String extractUsername(String jwt, boolean isRefreshToken) {
        return extractAuthClaims(jwt, Claims::getSubject,isRefreshToken);
    }


    @Override
    public String generateAuthToken(Map<String, Object> extraClaims, String userName) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(userName)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000*60*60))
                .signWith(getAuthSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isAuthTokenValid(String jwt, String username) {
        final String extractUsername = extractUsername(jwt, false);
        return (extractUsername.equals(username)) && !isTokenExpired(jwt, false);
    }

    @Override
    public boolean isRefreshTokenValid(String jwt, String username) {
        final String extractUsername = extractUsername(jwt, true);
        return (extractUsername.equals(username)) && !isTokenExpired(jwt, true);
    }

    @Override
    public Long extractRefreshUserId(String jwt) {
        Claims claims = extractRefreshClaims(jwt);
        return claims.get("id", Long.class);
    }

    @Override
    public String generateRefreshToken(Map<String, Object> extraClaims, String username) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 12))
                .signWith(getRefreshSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean isTokenExpired(String jwt, boolean isRefreshToken) {
        return extractExpiration(jwt,isRefreshToken).before(new Date());
    }

    private Date extractExpiration(String jwt, boolean isRefreshToken) {
        return extractAuthClaims(jwt, Claims::getExpiration, isRefreshToken);
    }


    public <T> T extractAuthClaims(String jwt, Function<Claims, T> claimsResolver, boolean isRefreshToken){
        final Claims claims = isRefreshToken ? extractRefreshClaims(jwt) : extractAuthClaims(jwt);
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

    private Claims extractRefreshClaims(String jwt) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getRefreshSigningKey())
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
