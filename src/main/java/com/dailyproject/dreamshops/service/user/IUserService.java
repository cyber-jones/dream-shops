package com.dailyproject.dreamshops.service.user;

import com.dailyproject.dreamshops.model.User;
import com.dailyproject.dreamshops.request.CreateUserRequest;
import com.dailyproject.dreamshops.request.UpdateUserRequest;

public interface IUserService {
    User getUserById(Long userId);
    User createUser(CreateUserRequest request);
    User updateUser(UpdateUserRequest request, Long userId);
    void deleteUser(Long userId);
}
