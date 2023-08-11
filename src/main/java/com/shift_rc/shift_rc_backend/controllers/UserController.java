package com.shift_rc.shift_rc_backend.controllers;

import com.shift_rc.shift_rc_backend.models.User;
import com.shift_rc.shift_rc_backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public Map<String, String> createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @GetMapping("/get")
    public Map<String, String> getUser(@RequestParam Long userId) {
        return userService.getUserById(userId);
    }

    @PostMapping("/update")
    public Map<String, String> updateUser(@RequestParam Long userId, @RequestBody Map<String, String> payload) {
        return userService.updateUser(userId, payload);
    }

    @PostMapping("/login")
    public Map<String, String> loginUser(@RequestBody Map<String, String> payload) {
        return userService.loginUser(payload);
    }
}


