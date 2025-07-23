package s5almiakki.oauth2clientjwtpractice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebMvc
public class WebMvcConfig implements WebMvcConfigurer {

    private final AllowedUris allowedUris;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .exposedHeaders("*")
                .allowedOrigins(allowedUris.getAllowedUris().toArray(new String[0]));
    }
}
