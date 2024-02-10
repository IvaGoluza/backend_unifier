package hr.fer.unifier.backend.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Map;

public interface JwtService {
    String extractUsername(String jwt,boolean isRefreshToken);
    String generateAuthToken(Map<String, Object> extraClaims, UserDetails userDetails);
    boolean isAuthTokenValid(String jwt, UserDetails userDetails);
    boolean isRefreshTokenValid(String jwt, UserDetails userDetails);
    boolean isTokenExpired(String jwt, boolean isRefreshToken);
    Long extractRefreshUserId(String jwt);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
