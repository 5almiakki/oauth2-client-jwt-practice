package s5almiakki.oauth2clientjwtpractice.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "custom")
public class AllowedUris {

    private List<String> allowedUris;

    public void validateRedirectUri(String uri) {
        if (uri == null) {
            throw new IllegalArgumentException("Missing redirect URI: " + uri);
        }
        try {
            URI redirectUri = new URI(uri);
            for (String allowed : allowedUris) {
                URI allowedUri = new URI(allowed);
                if (Objects.equals(redirectUri.getHost(), allowedUri.getHost())
                        && Objects.equals(redirectUri.getScheme(), allowedUri.getScheme())
                        && (redirectUri.getPort() != -1 || redirectUri.getPort() != allowedUri.getPort())) {
                    return;
                }
            }
        } catch (URISyntaxException e) {
            throw new IllegalArgumentException("Malformed redirect URI: " + uri, e);
        }
        throw new IllegalArgumentException("Invalid redirect URI: " + uri);
    }
}
