package com.example.session16.service.auth;

import com.example.session16.model.User;
import com.example.session16.repository.auth.AuthRepositoryImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImp implements AuthService {
    @Autowired
    private AuthRepositoryImp authRepositoryImp;

    @Override
    public boolean register(User user) {
        return authRepositoryImp.register(user);
    }

    @Override
    public User login(String username, String password) {
        return authRepositoryImp.login(username, password);
    }

    @Override
    public User findUserByName(String username) {
        return authRepositoryImp.findUserByName(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return authRepositoryImp.findUserByEmail(email);
    }
}
