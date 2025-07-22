package s5almiakki.oauth2clientjwtpractice.dto;

import java.util.Map;

public class NaverOAuth2Response implements OAuth2Response {

    private final Map<String, Object> attributes;

    public NaverOAuth2Response(Map<String, Object> attributes) {
        this.attributes = (Map<String, Object>) attributes.get("response");
    }

    @Override
    public String getProvider() {
        return "naver";
    }

    @Override
    public String getProviderId() {
        return attributes.get("id").toString();
    }

    @Override
    public String getName() {
        return attributes.get("name").toString();
    }
}
