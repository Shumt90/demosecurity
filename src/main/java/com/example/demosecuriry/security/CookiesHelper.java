package com.example.demosecuriry.security;

import org.springframework.http.HttpHeaders;

import javax.servlet.http.Cookie;

public class CookiesHelper {

    public static String getAuthorizationToken(Cookie[] cookies){
        if (cookies != null)
            for (var cookie : cookies) {
                if (cookie.getName().equals(HttpHeaders.AUTHORIZATION)) {
                    return cookie.getValue();
                }
            }

        return null;
    }
}
