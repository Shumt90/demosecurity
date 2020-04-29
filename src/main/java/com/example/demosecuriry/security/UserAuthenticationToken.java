package com.example.demosecuriry.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthenticationToken extends AbstractAuthenticationToken {

    private final User userDetails;
    private final boolean isTokenValid;

    public UserAuthenticationToken(User user, Collection<? extends GrantedAuthority> authorities, boolean isTokenValid) {
        super(authorities);
        this.userDetails=user;
        this.isTokenValid=isTokenValid;
    }

    @Override
    public Object getCredentials() {
        return  null;
    }

    @Override
    public Object getPrincipal() {
        return userDetails;
    }

    @Override
    public boolean isAuthenticated() {
        return isTokenValid;
    }
}
