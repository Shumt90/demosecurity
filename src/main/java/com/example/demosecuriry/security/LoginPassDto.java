package com.example.demosecuriry.security;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginPassDto {
    private String username;
    private String password;
    private boolean rememberMe;
}
