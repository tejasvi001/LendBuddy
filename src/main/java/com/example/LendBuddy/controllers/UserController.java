package com.example.LendBuddy.controllers;

import com.example.LendBuddy.dtos.UserDTO;
import com.example.LendBuddy.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(path="/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping
    public ResponseEntity<UserDTO> userProfile(){
        UserDTO user=userService.getUserProfile();
        return ResponseEntity.ok(user);
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserByID(@PathVariable Long id){
        UserDTO user=userService.findUserById(id);
        return ResponseEntity.ok(user);
    }
    @PatchMapping("/{id}")
    public ResponseEntity<UserDTO> updateProfile(@PathVariable Long id, @RequestBody Map updates){
        UserDTO user=userService.updateUser(id,updates);
        return ResponseEntity.ok(user);
    }
}
