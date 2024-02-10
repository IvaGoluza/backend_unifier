package hr.fer.unifier.backend.service;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.HashMap;
import java.util.Map;

public interface JwtService {
    String extractUsername(String jwt);
    String generateAuthToken(UserDetails userDetails);
    String generateAuthToken(Map<String, Object> extraClaims, UserDetails userDetails);
    boolean isTokenValid(String jwt, UserDetails userDetails);
    Long extractUserId(String jwt);
    String generateRefreshToken(UserDetails userDetails);
    String generateRefreshToken(Map<String, Object> extraClaims, UserDetails userDetails);
}
