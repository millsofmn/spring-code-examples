package com.millsofmn.securerest.web.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SecureRestApi {

    @GetMapping("/secure-rest")
    public ResponseEntity<String> getDefault(){
        return ResponseEntity.ok().body("My Content");
    }
}
