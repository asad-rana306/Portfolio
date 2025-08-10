package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IUserService {
    String signup(User user);
    ResponseEntity<String> login(User user);
    List<User> getAllUser();
}
