package com.dailyproject.dreamshops.data;

import java.util.Set;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.dailyproject.dreamshops.model.Role;
import com.dailyproject.dreamshops.model.User;
import com.dailyproject.dreamshops.repository.IRoleRepository;
import com.dailyproject.dreamshops.repository.IUserRepository;

import lombok.RequiredArgsConstructor;

@Component
@Transactional
@RequiredArgsConstructor
public class DataInitializer implements ApplicationListener<ApplicationReadyEvent> {
    private final IUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final IRoleRepository roleRepository;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        // Initialize data here
        createDefaultUsersIfNotExists();
        Set<String> defaultRoles = Set.of("USER", "ADMIN");
        CreateDefaultRoleIfNotExists(defaultRoles);
        createDefaultAdminIfNotExists();
    }


    private void createDefaultUsersIfNotExists() {
        Role userRole = roleRepository.findByName("USER").get();
        // Check if default users exist and create them if not
        for (int i = 1; i <= 5; i++) {
            String email = "user" + i + "@example.com";
            if (!userRepository.existsByEmail(email)) {
                User user = new User();
                user.setFirstName("User" + i);
                user.setLastName("Example");
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode("password" + i));
                user.setRoles(Set.of(userRole));
                userRepository.save(user);
                System.out.println("Created default user: " + email);
            }
        }
    }

    private void createDefaultAdminIfNotExists() {
        Role admRole = roleRepository.findByName("ADMIN").get();
        // Check if default users exist and create them if not
        for (int i = 1; i <= 2; i++) {
            String email = "admin" + i + "@example.com";
            if (!userRepository.existsByEmail(email)) {
                User user = new User();
                user.setFirstName("Admin" + i);
                user.setLastName("Admin");
                user.setEmail(email);
                user.setPassword(passwordEncoder.encode("password" + i));
                user.setRoles(Set.of(admRole));
                userRepository.save(user);
                System.out.println("Default admin user: " + email);
            }
        }
    }

    private void CreateDefaultRoleIfNotExists(Set<String> roleNames) {
        roleNames.stream()
            .filter(roleName -> !roleRepository.findByName(roleName).isPresent())
            .forEach(roleName -> {
                Role role = new Role();
                role.setName(roleName);
                roleRepository.save(role);
                System.out.println("Created default role: " + roleName);
            });
    }
}
