package s5almiakki.oauth2clientjwtpractice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.filter.CorsFilter;
import s5almiakki.oauth2clientjwtpractice.jwt.JwtFilter;
import s5almiakki.oauth2clientjwtpractice.oauth2.CustomOAuth2SuccessHandler;
import s5almiakki.oauth2clientjwtpractice.oauth2.OAuth2RedirectUriCookieFilter;
import s5almiakki.oauth2clientjwtpractice.service.CustomOAuth2UserService;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomOAuth2UserService customOAuth2UserService;
    private final CustomOAuth2SuccessHandler customOAuth2SuccessHandler;
    private final JwtFilter jwtFilter;
    private final OAuth2RedirectUriCookieFilter oAuth2RedirectUriCookieFilter;
    private final AllowedUris allowedUris;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(cors -> cors
                        .configurationSource(request -> {
                            CorsConfiguration config = new CorsConfiguration();
                            config.setAllowedOrigins(allowedUris.getAllowedUris());
                            config.setAllowedMethods(Collections.singletonList("*"));
                            config.setAllowCredentials(true);
                            config.setAllowedHeaders(Collections.singletonList("*"));
                            config.setMaxAge(3600L);
                            config.setExposedHeaders(List.of("Set-Cookie", "Authorization"));
                            return config;
                        }))
                .csrf(csrf -> csrf.disable())
                .formLogin(formLogin -> formLogin.disable())
                .httpBasic(httpBasic -> httpBasic.disable())
                .oauth2Login(oAuth2Login -> oAuth2Login
                        .userInfoEndpoint(userInfoEndpoint -> userInfoEndpoint
                                .userService(customOAuth2UserService))
                        .successHandler(customOAuth2SuccessHandler))
                .authorizeHttpRequests(httpRequest -> httpRequest
                        .requestMatchers("/").permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(oAuth2RedirectUriCookieFilter, CorsFilter.class)
                .build();
    }
}
