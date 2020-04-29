package com.example.demosecuriry.security;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.Payload;
import com.nimbusds.jose.crypto.MACVerifier;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Service;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.example.demosecuriry.security.CookiesHelper.getAuthorizationToken;

@Service
@RequiredArgsConstructor
public class TokenValidatorFilter implements Filter {

    @Value("${app.secret}")
    private String secret;

    private final ObjectMapper om;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        var request = (HttpServletRequest) servletRequest;

        String strToken = getAuthorizationToken(request.getCookies());

        if (strToken != null) {

            boolean verified = false;
            JWSObject parsed = null;

            try {
                parsed = JWSObject.parse(strToken);
                verified = parsed.verify(new MACVerifier(secret));
            } catch (Exception ignored) {

            }

            if (verified) {
                var userDto = om.readValue(parsed.getPayload().toString(), UserDto.class);
                var user = UserDto.to(userDto);

                SecurityContextHolder.setContext(new SecurityContextImpl(new UserAuthenticationToken(user, user.getAuthorities(), true)));
            }
        }

        filterChain.doFilter(servletRequest, servletResponse);

    }
}
