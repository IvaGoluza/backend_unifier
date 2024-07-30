package hr.fer.unifier.backend.service;

import java.util.Map;

public interface JwtService {
    String extractUsername(String jwt,boolean isRefreshToken);
    String generateAuthToken(Map<String, Object> extraClaims, String username);
    boolean isAuthTokenValid(String jwt, String username);
    boolean isRefreshTokenValid(String jwt, String username);
    boolean isTokenExpired(String jwt, boolean isRefreshToken);
    Long extractRefreshUserId(String jwt);
    String generateRefreshToken(Map<String, Object> extraClaims, String username);

    Long extractUserId(String jwt);
}
