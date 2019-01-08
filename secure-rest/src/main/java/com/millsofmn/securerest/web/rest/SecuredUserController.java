package com.millsofmn.securerest.web.rest;

import com.millsofmn.securerest.domain.User;
import com.millsofmn.securerest.service.UserAuthenticationService;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
public class SecuredUserController {

    @NonNull
    UserAuthenticationService authentication;

    @GetMapping("/current")
    public User getCurrent(@AuthenticationPrincipal User user){
        System.out.println("Logged in user is : " + user.getUsername() + " " + user.getId());
        return user;
    }

    @GetMapping("/logout")
    public boolean logout(@AuthenticationPrincipal User user){
        authentication.logout(user);
        return true;
    }
}
