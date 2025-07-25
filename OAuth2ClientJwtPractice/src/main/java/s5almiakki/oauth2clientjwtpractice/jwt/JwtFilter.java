package s5almiakki.oauth2clientjwtpractice.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = null;
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("accessToken")) {
                accessToken = cookie.getValue();
                break;
            }
        }
        if (accessToken == null || jwtUtils.isExpired(accessToken)) {
            filterChain.doFilter(request, response);
            return;
        }
        Authentication authentication = new UsernamePasswordAuthenticationToken(jwtUtils.getUsername(accessToken), null, null);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);
    }
}
