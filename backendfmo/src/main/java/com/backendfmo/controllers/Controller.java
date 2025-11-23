package com.backendfmo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.backendfmo.services.UserServiceImpl;

@RestController
@RequestMapping("/api")
public class Controller {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/status")
    public String getStatus() {
        return "API is running";
    }
    @GetMapping("/users")
    public ResponseEntity<?> getUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
