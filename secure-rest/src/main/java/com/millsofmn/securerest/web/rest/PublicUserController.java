package com.millsofmn.securerest.web.rest;

import com.millsofmn.securerest.service.UserAuthenticationService;
import com.millsofmn.securerest.domain.User;
import com.millsofmn.securerest.service.UserService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/public/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class PublicUserController {

    @NonNull
    UserAuthenticationService authentication;
    @NonNull
    UserService users;

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password){
        System.out.println("registering : " + username);
        users.save(
                User.builder()
                        .id(username)
                        .username(username)
                        .password(password)
                        .build()
        );
        return login(username, password);
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        System.out.println("Logging in " + username);
        return authentication
                .login(username, password)
                .orElseThrow(() -> new RuntimeException("invalid username and/or password"));
    }
}
