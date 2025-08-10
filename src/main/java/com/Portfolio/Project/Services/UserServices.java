package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.User;
import com.Portfolio.Project.Repository.UserRepository;
import com.Portfolio.Project.Utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServices implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

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

    public boolean logine(String userName, String rawPassword) {
        return userRepository.findByUserName(userName)
                .map(user -> passwordEncoder.matches(rawPassword, user.getPassword()))
                .orElse(false);
    }
    public ResponseEntity<String> login(User user) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getUserName(), user.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(user.getUserName());
            String jwt = jwtUtil.generateToken(userDetails.getUsername());
            return new ResponseEntity<>(jwt, HttpStatus.OK);
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Incorrect Password", HttpStatus.BAD_REQUEST);
        }
    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }
}
