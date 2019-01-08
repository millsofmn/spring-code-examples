package com.millsofmn.securerest.service;

import com.millsofmn.securerest.domain.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements  UserService {

    private Map<String, User> users = new HashMap<>();

    @Override
    public User save(User user) {
        System.out.println("saving user : " + user.getUsername() + " " + user.getId());
        return users.put(user.getId(), user);
    }

    @Override
    public Optional<User> find(String id) {
        System.out.println("find user by id : " + id);
        return Optional.ofNullable(users.get(id));
    }

    @Override
    public Optional<User> findByUsername(String username) {
        System.out.println("find user by username : " + username);
        return users.values().stream().filter(u -> Objects.equals(username, u.getUsername())).findFirst();
    }
}
