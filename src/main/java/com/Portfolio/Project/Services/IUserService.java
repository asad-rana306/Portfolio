package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.User;

import java.util.List;

public interface IUserService {
    String signup(User user);
    boolean login(String userName, String password);
    List<User> getAllUser();
}
