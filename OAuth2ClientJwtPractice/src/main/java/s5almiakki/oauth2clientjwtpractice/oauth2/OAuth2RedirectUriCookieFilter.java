package s5almiakki.oauth2clientjwtpractice.oauth2;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import s5almiakki.oauth2clientjwtpractice.config.AllowedUris;

import java.io.IOException;

@RequiredArgsConstructor
@Slf4j
@Component
public class OAuth2RedirectUriCookieFilter extends OncePerRequestFilter {

    @Value("${custom.oauth2.redirect-uri-param-name}")
    private String redirectUriParamName;

    private final AllowedUris allowedUris;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!request.getRequestURI().startsWith("/oauth2/authorization")) {
            filterChain.doFilter(request, response);
            return;
        }
        String redirectUri = request.getParameter(redirectUriParamName);
        allowedUris.validateRedirectUri(redirectUri);
        Cookie cookie = new Cookie(redirectUriParamName, redirectUri);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        cookie.setMaxAge(180);
        response.addCookie(cookie);
        filterChain.doFilter(request, response);
    }
}
