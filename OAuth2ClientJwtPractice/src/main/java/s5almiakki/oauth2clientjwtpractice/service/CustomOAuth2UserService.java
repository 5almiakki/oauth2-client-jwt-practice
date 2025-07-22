package s5almiakki.oauth2clientjwtpractice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import s5almiakki.oauth2clientjwtpractice.dto.CustomOAuth2User;
import s5almiakki.oauth2clientjwtpractice.dto.NaverOAuth2Response;
import s5almiakki.oauth2clientjwtpractice.dto.OAuth2Response;
import s5almiakki.oauth2clientjwtpractice.entity.UserEntity;
import s5almiakki.oauth2clientjwtpractice.repository.UserRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("User {} loaded", oAuth2User);
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        OAuth2Response oAuth2Response = null;
        switch (registrationId) {
            case "naver":
                oAuth2Response = new NaverOAuth2Response(oAuth2User.getAttributes());
                break;
            default:
                return null;
        }
        String username = oAuth2Response.getProvider() + "_" + oAuth2Response.getProviderId();
        String name = oAuth2Response.getName();
        Optional<UserEntity> oUser = userRepository.findByUsername(username);
        UserEntity user;
        if (oUser.isEmpty()) {
            user = userRepository.save(new UserEntity(username, name, "ROLE_USER"));
        } else {
            user = oUser.get();
            user.updateName(name);
        }
        return new CustomOAuth2User(name, user.getRole(), username);
    }
}
