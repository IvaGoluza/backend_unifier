package hr.fer.unifier.backend.config.filters;

import hr.fer.unifier.backend.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    private final UserDetailsService userDetailsService;

    private final RequestAttributeSecurityContextRepository repo;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (Objects.isNull(authHeader) || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String jwt = authHeader.substring(7);
        final String username = jwtService.extractUsername(jwt, false);

        if (!Objects.isNull(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if (Objects.isNull(userDetails)) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "");
            }

            if (jwtService.isAuthTokenValid(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

                authToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authToken);
//                final SecurityContext securityContext = SecurityContextHolder.getContext();
//                securityContext.setAuthentication(authToken);
//                final SecurityContextChangedListener securityContextChangedListener = new ObservationSecurityContextChangedListener()
//                final SecurityContextHolderStrategy securityContextHolderStrategy = new ListeningSecurityContextHolderStrategy();
//                securityContextHolderStrategy.setContext(securityContext);
//                securityContextHolderStrategy.setDeferredContext(() -> securityContext);
//                repo.setSecurityContextHolderStrategy(securityContextHolderStrategy);
            }
            filterChain.doFilter(request, response);
        }

    }
}
