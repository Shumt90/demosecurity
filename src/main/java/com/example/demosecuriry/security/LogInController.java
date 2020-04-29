package com.example.demosecuriry.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.MACSigner;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Payload;

@RequestMapping("/authentication")
@RestController
@AllArgsConstructor
public class LogInController {

    private final LoginService loginService;

    @PostMapping
    public void login(@RequestBody LoginPassDto loginPass, HttpServletResponse response){
        loginService.checkAndLogin(loginPass, response);
    }

    @DeleteMapping
    public void delete(HttpServletResponse response){
        var cookie = new Cookie(HttpHeaders.AUTHORIZATION, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
