package com.dailyproject.dreamshops.data;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.dailyproject.dreamshops.model.User;
import com.dailyproject.dreamshops.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final IUserRepository userRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Initialize data here
        createDefaultUsersIfNotExists();
    }


    private void createDefaultUsersIfNotExists() {
        // Check if default users exist and create them if not
        for (int i = 1; i <= 5; i++) {
            String email = "user" + i + "@example.com";
            if (!userRepository.existsByEmail(email)) {
                User user = new User();
                user.setFirstName("User" + i);
                user.setLastName("Example");
                user.setEmail(email);
                user.setPassword("password");
                userRepository.save(user);
                System.out.println("Created default user: " + email);
            }
        }
    }
}
