package com.millsofmn.securerest.service;

import com.millsofmn.securerest.domain.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

//@Service
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UUIDAuthenticationService implements UserAuthenticationService {

    @NonNull UserService users;

    @Override
    public Optional<String> login(String username, String password){
        System.out.println("Login : " + username);
        String uuid = UUID.randomUUID().toString();

        User user = User.builder()
                .id(uuid)
                .username(username)
                .password(password)
                .build();

        users.save(user);

        return Optional.of(uuid);
    }

    @Override
    public Optional<User> findByToken(String token) {
        System.out.println("Look for UUID token : " + token);
        return users.find(token);
    }

    @Override
    public void logout(User user) {

    }


}
