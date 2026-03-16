package com.auction.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.auction.model.User;
import com.auction.service.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "https://onlineauction-frontend.onrender.com")
public class UserController {

    @Autowired
    private UserService userService;

    // Register User
    @PostMapping
    public User registerUser(@RequestBody User user) {
        return userService.saveUser(user);
    }

    // Get All Users
    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    // Delete User
    @DeleteMapping("/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "User deleted successfully";
    }
}