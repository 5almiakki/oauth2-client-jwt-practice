package s5almiakki.oauth2clientjwtpractice.jwt;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtUtils {

    private final String issuer = "s5almiakki";
    private final SecretKey secretKey;
    private final JwtParser jwtParser;

    public JwtUtils(
            @Value("${custom.jwt.secret}")
            String secret
    ) {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        jwtParser = Jwts.parser().verifyWith(secretKey).build();
    }

    public String createJwt(String username, String role, long ttlMillis) {
        long nowMillis = System.currentTimeMillis();
        return Jwts.builder()
                .claim("username", username)
                .claim("role", role)
                .issuer(issuer)
                .issuedAt(new Date(nowMillis))
                .expiration(new Date(nowMillis + ttlMillis))
                .signWith(secretKey)
                .compact();
    }

    public String getUsername(String jwt) {
        return jwtParser.parseSignedClaims(jwt).getPayload().get("username").toString();
    }

    public String getRole(String jwt) {
        return jwtParser.parseSignedClaims(jwt).getPayload().get("role").toString();
    }

    public boolean isExpired(String jwt) {
        return jwtParser.parseSignedClaims(jwt).getPayload().getExpiration().getTime() < System.currentTimeMillis();
    }
}
