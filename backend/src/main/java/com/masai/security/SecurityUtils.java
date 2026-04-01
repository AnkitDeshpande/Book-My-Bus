package com.masai.security;

import com.masai.enums.UserRole;
import com.masai.exception.UnauthorizedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private SecurityUtils() {}

    public static UserPrincipal getCurrentPrincipal() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !(auth.getPrincipal() instanceof UserPrincipal)) {
            throw new UnauthorizedException("No authenticated user found");
        }
        return (UserPrincipal) auth.getPrincipal();
    }

    public static Long getCurrentUserId() {
        return getCurrentPrincipal().getUserId();
    }

    public static boolean isAdmin() {
        return getCurrentPrincipal().getRole() == UserRole.ADMIN;
    }
}
