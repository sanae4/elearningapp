package com.example.elearning.service;

import com.example.elearning.model.User;

public interface AuthService {
    String authenticate(String email, String password);

    void registerUser(User user);
}