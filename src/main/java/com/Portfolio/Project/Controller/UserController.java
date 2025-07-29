package com.Portfolio.Project.Controller;

import com.Portfolio.Project.Model.User;
import com.Portfolio.Project.Response.ApiResponse;
import com.Portfolio.Project.Services.IUserService;
import com.Portfolio.Project.Services.UserServices;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CONFLICT;
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userServices;

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
}
