package com.dailyproject.dreamshops.service.user;

import com.dailyproject.dreamshops.dto.UserDto;
import com.dailyproject.dreamshops.model.User;
import com.dailyproject.dreamshops.request.CreateUserRequest;
import com.dailyproject.dreamshops.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
    UserDto convertUserToDto(User user);
    User getAuthenticatedUser();
}
