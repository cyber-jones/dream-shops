package com.dailyproject.dreamshops.service.user;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.dailyproject.dreamshops.dto.UserDto;
import com.dailyproject.dreamshops.exceptions.AlreadyExistsException;
import com.dailyproject.dreamshops.exceptions.ResourceNotFoundException;
import com.dailyproject.dreamshops.model.User;
import com.dailyproject.dreamshops.repository.IUserRepository;
import com.dailyproject.dreamshops.request.CreateUserRequest;
import com.dailyproject.dreamshops.request.UpdateUserRequest;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor    
public class UserService implements IUserService {
    private final ModelMapper modelMapper;
    private final IUserRepository userRepository;

    @Override
    public User getUserById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public UserDto createUser(CreateUserRequest request) {
        return Optional.of(request)
            .filter(user -> !userRepository.existsByEmail(user.getEmail()))
            .map(req -> {
                User user = new User();
                user.setFirstName(req.getFirstName());
                user.setLastName(req.getLastName());
                user.setEmail(req.getEmail());
                user.setPassword(req.getPassword());
                return userRepository.save(user);
            }).map(this::convertUserToDto)
            // .map(userRepository :: save) 
            .orElseThrow(() -> new AlreadyExistsException("Oops! User with email " + request.getEmail() + " already exists"));
    }

    @Override
    public UserDto updateUser(UpdateUserRequest request, Long userId) {
        return userRepository.findById(userId)
            .map(user -> {
                user.setFirstName(request.getFirstName());
                user.setLastName(request.getLastName());
                return userRepository.save(user);
            })
            .map(this::convertUserToDto)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.findById(userId)
            .ifPresentOrElse(userRepository :: delete, () -> { 
                throw new ResourceNotFoundException("User not found!"); 
        });
    }

    @Override
    public UserDto convertUserToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }
}
