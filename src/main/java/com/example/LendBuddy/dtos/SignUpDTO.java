package com.example.LendBuddy.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class SignUpDTO {
    private String email;
    private String name;
    private String password;
    private String phone;
//    private Set<Role> roles;
//    private Set<Permission> permissions;
}
