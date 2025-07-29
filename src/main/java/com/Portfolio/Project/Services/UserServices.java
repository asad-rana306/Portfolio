package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.User;
import com.Portfolio.Project.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
@Service
@RequiredArgsConstructor
public class UserServices implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public String signup(User user) {
        if (userRepository.findByUserName(user.getUserName()).isPresent())
            return "user already exist";

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return "user registered successfully";
    }

    public boolean login(String userName, String rawPassword) {
        return userRepository.findByUserName(userName)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }
}
