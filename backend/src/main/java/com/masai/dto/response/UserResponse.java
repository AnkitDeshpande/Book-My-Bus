package com.masai.dto.response;

import com.masai.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String phone;
    private UserRole role;
    private boolean active;
    private LocalDateTime createdAt;
}
