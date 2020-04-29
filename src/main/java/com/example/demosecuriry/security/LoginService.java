package com.example.demosecuriry.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACSigner;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
@RequiredArgsConstructor
public class LoginService {

    @Value("${app.secret}")
    private String secret;

    private final ObjectMapper om;

    private final UserDetailsService userDetailsService;

    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    public void checkAndLogin(LoginPassDto loginPass, HttpServletResponse response) {

        try {

            if (loginPass.getUsername() == null) {
                response.setStatus(400);
                return;
            }

            var user = userDetailsService.loadUserByUsername(loginPass.getUsername());

            if (passwordEncoder.matches(loginPass.getPassword(), user.getPassword())) {

                var userDto = UserDto.from(user);

                JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256),
                        new Payload(om.writeValueAsString(userDto)));

                jwsObject.sign(new MACSigner(secret));

                response.setStatus(200);

                var cookie = new Cookie(HttpHeaders.AUTHORIZATION, jwsObject.serialize());

                if (loginPass.isRememberMe())
                    cookie.setMaxAge(Integer.MAX_VALUE);

                response.addCookie(cookie);
                return;

            } else {
                response.setStatus(401);
            }
        } catch (UsernameNotFoundException ex) {
            response.setStatus(401);
        }
        ;
    }

}
