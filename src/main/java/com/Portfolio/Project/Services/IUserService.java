package com.Portfolio.Project.Services;

import com.Portfolio.Project.Model.User;

public interface IUserService {
    String signup(User user);
    boolean login(String userName, String password);
}
