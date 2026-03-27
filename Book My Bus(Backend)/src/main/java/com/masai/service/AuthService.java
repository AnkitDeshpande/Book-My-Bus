package com.masai.service;

import com.masai.dto.request.LoginRequest;
import com.masai.dto.request.RegisterRequest;
import com.masai.dto.response.LoginResponse;
import com.masai.dto.response.UserResponse;

public interface AuthService {

    UserResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);

    void activateAccount(String token);

    void resendActivation(String email);

    void forgotPassword(String email);

    void resetPassword(String token, String newPassword);
}
