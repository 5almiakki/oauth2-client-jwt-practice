package s5almiakki.oauth2clientjwtpractice.oauth2;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import s5almiakki.oauth2clientjwtpractice.dto.CustomOAuth2User;
import s5almiakki.oauth2clientjwtpractice.jwt.JwtUtils;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class CustomOAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final JwtUtils jwtUtils;
    private final AllowedRedirectUris allowedRedirectUris;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String redirectUri = request.getParameter("redirect_uri");
        validateRedirectUri(redirectUri);
        CustomOAuth2User user = (CustomOAuth2User) authentication.getPrincipal();
        String username = user.getUsername();
        GrantedAuthority grantedAuthority = authentication.getAuthorities().iterator().next();
        String role = grantedAuthority.getAuthority();
        String token = jwtUtils.createJwt(username, role, 60 * 1000L);
        Cookie cookie = new Cookie("token", token);
        cookie.setMaxAge(60 * 1000);
        cookie.setPath("/");
        cookie.setHttpOnly(true);
        response.addCookie(cookie);
        response.sendRedirect(redirectUri);
    }

    private void validateRedirectUri(String uri) {
        for (String allowedRedirectUri : allowedRedirectUris.getAllowedRedirectUris()) {
            if (allowedRedirectUri.startsWith(uri)) {
                return;
            }
        }
        throw new IllegalArgumentException("Invalid redirect URI: " + uri);
    }
}
