package com.ko.board.service;

import com.ko.board.dto.*;
import com.ko.board.entity.User;
import com.ko.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);

        log.info("oAuth2User : {}", oAuth2User);

        OAuth2Response oAuth2Response = null;
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        switch (registrationId) {
            case "naver":
                oAuth2Response = new NaverResponse(oAuth2User.getAttributes());
                break;

            case "google":
                oAuth2Response = new GoogleResponse(oAuth2User.getAttributes());
                break;

            case "kakao":
                oAuth2Response = new KakaoResponse(oAuth2User.getAttributes());
                break;

            default:
                return null;
        }

        String username = oAuth2Response.getProvider() + " " + oAuth2Response.getProviderId();

        User existUser = userRepository.findByUsername(username);
        if (existUser == null) {
            User user = new User(username, oAuth2Response.getName(), oAuth2Response.getEmail(), "ROLE_USER");
            userRepository.save(user);
            UserDTO userDTO = new UserDTO("ROLE_USER", oAuth2Response.getName(), username);
            return new CustomOAuth2User(userDTO);
        }
        // 이미 존재하는 회원이라면 update
        else {
            existUser.updateNameAndEmail(oAuth2Response.getName(), oAuth2Response.getEmail());
            UserDTO userDTO = new UserDTO("ROLE_USER", oAuth2Response.getName(), existUser.getUsername());
            return new CustomOAuth2User(userDTO);
        }

    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        CustomOAuth2User customUserDetails = (CustomOAuth2User) authentication.getPrincipal();
        String username = customUserDetails.getUsername();

        return userRepository.findByUsername(username);
    }
}
