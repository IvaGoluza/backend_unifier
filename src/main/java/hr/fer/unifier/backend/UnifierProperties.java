package hr.fer.unifier.backend;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "unifier")
@NoArgsConstructor
@Component
@Data
public class UnifierProperties {
    @NonNull
    private String authSecretKey;

    @NonNull
    private String refreshSecretKey;

    @ConfigurationProperties(prefix = "email-properties")
    @NoArgsConstructor
    @Component
    @Data
    public static class EmailProperties {
        @NonNull
        private String host;
        @NonNull
        private Integer port;
        @NonNull
        private String username;
        @NonNull
        private String password;
    }

    @ConfigurationProperties(prefix = "unifier-frontend-url")
    @NoArgsConstructor
    @Component
    @Data
    public static class UnifierFrontendUrlProperties {
        @NonNull
        private String host;
        @NonNull
        private Integer port;
        @NonNull
        private String protocol;
        @NonNull
        private String passwordRecoveryUrl;
        public String getRecoveryUrl(){
            return String.format("%s://%s:%d/%s", protocol, host, port, passwordRecoveryUrl);
        }
    }
}
