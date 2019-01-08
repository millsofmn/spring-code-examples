package com.millsofmn.securerest.service;

import com.millsofmn.securerest.domain.User;

import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> find(String id);

    Optional<User> findByUsername(String username);
}
