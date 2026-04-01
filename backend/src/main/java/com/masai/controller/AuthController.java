package com.masai.controller;

import com.masai.dto.ApiResponse;
import com.masai.dto.request.ForgotPasswordRequest;
import com.masai.dto.request.LoginRequest;
import com.masai.dto.request.RegisterRequest;
import com.masai.dto.request.ResetPasswordRequest;
import com.masai.dto.response.LoginResponse;
import com.masai.dto.response.UserResponse;
import com.masai.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Register, login, and account management endpoints")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    @Operation(summary = "Register a new user")
    public ResponseEntity<ApiResponse<UserResponse>> register(@Valid @RequestBody RegisterRequest request) {
        UserResponse user = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Registration successful. Please check your email to activate your account.", user));
    }

    @GetMapping("/activate")
    @Operation(summary = "Activate user account via email link")
    public ResponseEntity<ApiResponse<Void>> activate(@RequestParam String token) {
        authService.activateAccount(token);
        return ResponseEntity.ok(ApiResponse.success("Account activated successfully. You can now log in.", null));
    }

    @PostMapping("/resend-activation")
    @Operation(summary = "Resend account activation email")
    public ResponseEntity<ApiResponse<Void>> resendActivation(@RequestParam String email) {
        authService.resendActivation(email);
        return ResponseEntity.ok(ApiResponse.success("Activation email resent. Please check your inbox.", null));
    }

    @PostMapping("/login")
    @Operation(summary = "Login and receive a JWT token")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(ApiResponse.success("Login successful", response));
    }

    @PostMapping("/forgot-password")
    @Operation(summary = "Request a password reset email")
    public ResponseEntity<ApiResponse<Void>> forgotPassword(@Valid @RequestBody ForgotPasswordRequest request) {
        authService.forgotPassword(request.getEmail());
        return ResponseEntity.ok(ApiResponse.success("Password reset email sent. Please check your inbox.", null));
    }

    @PostMapping("/reset-password")
    @Operation(summary = "Reset password using token from email")
    public ResponseEntity<ApiResponse<Void>> resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok(ApiResponse.success("Password reset successfully. You can now log in.", null));
    }
}
