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
}
