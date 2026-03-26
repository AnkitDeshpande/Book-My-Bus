package com.masai.dto.response;

import com.masai.enums.UserRole;
import lombok.Getter;

@Getter
public class LoginResponse {

    private final String token;
    private final String tokenType = "Bearer";
    private final Long userId;
    private final String username;
    private final String email;
    private final UserRole role;

    public LoginResponse(String token, Long userId, String username, String email, UserRole role) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.role = role;
    }
}
