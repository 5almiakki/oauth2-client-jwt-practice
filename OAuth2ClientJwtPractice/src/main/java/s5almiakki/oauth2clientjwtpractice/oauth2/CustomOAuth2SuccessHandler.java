package s5almiakki.oauth2clientjwtpractice.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import s5almiakki.oauth2clientjwtpractice.config.AllowedUris;
import s5almiakki.oauth2clientjwtpractice.dto.CustomOAuth2User;
import s5almiakki.oauth2clientjwtpractice.jwt.JwtUtils;

import java.io.IOException;
import java.util.Arrays;

@RequiredArgsConstructor
@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${custom.oauth2.redirect-uri-param-name}")
    private String redirectUriParamName;

    private final JwtUtils jwtUtils;
    private final AllowedUris allowedUris;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUri = Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(redirectUriParamName))
                .map(Cookie::getValue)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Redirect URI parameter not found"));
        allowedUris.validateRedirectUri(redirectUri);
        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();
        String username = user.getUsername();
        GrantedAuthority grantedAuthority = authentication.getAuthorities().iterator().next();
        String role = grantedAuthority.getAuthority();
        String jwt = jwtUtils.createJwt(username, role, 60 * 1000L);
        Cookie cookie = new Cookie("jwt", jwt);
        cookie.setMaxAge(60 * 1000);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.sendRedirect(redirectUri);
    }
}
