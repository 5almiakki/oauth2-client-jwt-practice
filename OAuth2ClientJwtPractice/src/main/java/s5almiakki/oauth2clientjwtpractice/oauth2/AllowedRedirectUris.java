package s5almiakki.oauth2clientjwtpractice.oauth2;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom.oauth2")
public class AllowedRedirectUris {

    private String[] allowedRedirectUris;
}
