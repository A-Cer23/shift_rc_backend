package com.shift_rc.shift_rc_backend.services;

import com.shift_rc.shift_rc_backend.models.User;
import com.shift_rc.shift_rc_backend.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public Map<String, String> createUser(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            return getResponse(null, 406, "Email already exists");
        }
        return getResponse(userRepo.save(user), 201, null);
    }

    public Map<String, String> getUserById(Long userId) {
        User foundUser = userRepo.findById(userId).orElse(null);
        if (foundUser == null) {
            return getResponse(null, 406, String.format("User ID: %s does not exist", userId.toString()));
        }
        return getResponse(foundUser, 201, null);
    }

    public Map<String, String> updateUser(Long userId, Map<String, String> payload) {

        userRepo.updateUserInfo(
            userId,
            payload.get("firstName"),
            payload.get("lastName")
        );

        User foundUser = userRepo.findById(userId).orElse(null);
        if (foundUser == null) {
            return getResponse(null, 406, String.format("User ID: %s does not exist", userId.toString()));
        }

       return getResponse(foundUser, 201, null);
    }

    public Map<String, String> loginUser(Map<String, String> payload) {

        User loggedInUser = userRepo.loginUser(payload.get("email"), payload.get("password"));

        if (loggedInUser == null) {
            return getResponse(null, 406, "Wrong email and/or password");
        }
        return getResponse(loggedInUser, 201, null);
    }


    public Map<String, String> getResponse(User user, Integer status, String error) {

        HashMap<String, String> response = new HashMap<>();

        switch(status) {
            case 201:
                response.put("status", status.toString());
                break;
            case 406:
                response.put("status", status.toString());
                response.put("error", error);
                break;
        }

        if (user != null) {
            response.put("userId", user.getId().toString());
            response.put("firstName", user.getFirstName());
            response.put("lastName", user.getLastName());
            response.put("email", user.getEmail());
        }

        return response;
    }
}
