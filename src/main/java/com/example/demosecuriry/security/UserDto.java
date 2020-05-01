package com.example.demosecuriry.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private String username;
    private List<String> authorities=new ArrayList<>();
    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;
    private long iat;

    public static User to(UserDto dto){

        List<SimpleGrantedAuthority> authorities = dto.getAuthorities().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        return User.builder()
                .username(dto.username)
                .accountNonExpired(dto.accountNonExpired)
                .accountNonLocked(dto.accountNonLocked)
                .credentialsNonExpired(dto.credentialsNonExpired)
                .enabled(dto.enabled)
                .authorities(authorities)
                .iat(dto.iat)
                .build();
    }

    public static UserDto from(UserDetails user, long IssuedAt) {

        List<String> authorities = user.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        return UserDto.builder()
                .username(user.getUsername())
                .accountNonExpired(user.isAccountNonExpired())
                .accountNonLocked(user.isAccountNonLocked())
                .credentialsNonExpired(user.isCredentialsNonExpired())
                .enabled(user.isEnabled())
                .authorities(authorities)
                .iat(IssuedAt)
                .build();
    }
}
