package hr.fer.unifier.backend.config;

import hr.fer.unifier.backend.db.user.UserDao;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableConfigurationProperties
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

  private final UserDao userDao;

  @Bean
  public ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  public UserDetailsService userDetailsService() {
    return username -> userDao.findByEmail(username).orElse(null);
  }
  @Bean
  public AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

    authProvider.setUserDetailsService(userDetailsService());
    authProvider.setPasswordEncoder(passwordEncoder());

    return authProvider;
  }

  @Bean
  public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
  }
}
