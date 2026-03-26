package com.masai.service;

import com.masai.dto.PagedResponse;
import com.masai.dto.request.UpdateProfileRequest;
import com.masai.dto.response.UserResponse;

public interface UserService {

    UserResponse getMyProfile();

    UserResponse updateMyProfile(UpdateProfileRequest request);

    PagedResponse<UserResponse> getAllUsers(int page, int size);

    void deleteUser(Long userId);
}
