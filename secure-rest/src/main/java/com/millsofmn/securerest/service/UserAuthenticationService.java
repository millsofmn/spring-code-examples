package com.millsofmn.securerest.service;

import com.millsofmn.securerest.domain.User;

import java.util.Optional;

public interface UserAuthenticationService {

    Optional<String> login(String username, String password);

    Optional<User> findByToken(String token);

    void logout(User user);
}
