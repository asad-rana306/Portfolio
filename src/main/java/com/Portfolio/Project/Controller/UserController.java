package com.Portfolio.Project.Controller;

import com.Portfolio.Project.Model.User;
import com.Portfolio.Project.Response.ApiResponse;
import com.Portfolio.Project.Services.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;



import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userServices;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUser(){
        try {
            List<User> user = userServices.getAllUser();
            return ResponseEntity.ok(new ApiResponse("users",user));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND)
                    .body(new ApiResponse(e.getMessage(),null));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody User loginRequest) {
        boolean loginSuccess = userServices.login(loginRequest.getUserName(), loginRequest.getPassword());

        if (loginSuccess) {
            return ResponseEntity.ok(new ApiResponse("User logged in successfully", null));
        } else {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse("Invalid username or password", null));
        }
    }

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse> signup(@RequestBody User user) {
        String result = userServices.signup(user);

        if (result.equalsIgnoreCase("user already exist")) {
            return ResponseEntity.status(CONFLICT)
                    .body(new ApiResponse(result, null));
        } else {
            return ResponseEntity.ok(new ApiResponse(result, null));
        }
    }
    @GetMapping("/api/user/me")
    public ResponseEntity<?> getCurrentUser(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(Map.of(
                "username", userDetails.getUsername(),
                "roles", userDetails.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        ));
    }

}
