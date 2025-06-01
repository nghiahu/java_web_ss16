package com.example.session16.repository.auth;

import com.example.session16.model.User;

public interface AuthRepository {
    boolean register(User user);
    User login(String username, String password);
    User findUserByName(String username);
    User findUserByEmail(String email);
}
