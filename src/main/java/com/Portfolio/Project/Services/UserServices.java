package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.User;
import com.Portfolio.Project.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServices implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public String signup(User user) {
        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            return "user already exists";
        }
        user.setUserName(user.getUserName());
        user.setEmail(user.getEmail());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        List<String> incomingRoles = user.getRole();
        if (incomingRoles == null || incomingRoles.isEmpty()) {
            user.setRole(Collections.singletonList("USER"));
        } else {
            user.setRole(incomingRoles.stream().map(String::toUpperCase).toList());
        }
        userRepository.save(user);
        return "user registered successfully";
    }

    public boolean login(String userName, String rawPassword) {
        return userRepository.findByUserName(userName)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
