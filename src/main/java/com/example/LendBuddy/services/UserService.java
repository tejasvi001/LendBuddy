package com.example.LendBuddy.services;

import com.example.LendBuddy.dtos.SignUpDTO;
import com.example.LendBuddy.dtos.UserDTO;
import com.example.LendBuddy.entities.UserEntity;
import com.example.LendBuddy.exceptions.ResourceNotFoundException;
import com.example.LendBuddy.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;


    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.modelMapper=modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;

    }
    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            return userRepository.findByEmail(username).orElseThrow(
                    ()->new BadCredentialsException("no user with the email "+username+" exists")
            );
        } catch (BadCredentialsException e) {
            throw new RuntimeException(e);
        }
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        Optional<UserEntity> user=userRepository.findByEmail(signUpDTO.getEmail());
        if(user.isPresent()){
            throw new BadCredentialsException("User with email already exists "+signUpDTO.getEmail());
        }
        UserEntity toCreate=modelMapper.map(signUpDTO,UserEntity.class);
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));
        UserEntity savedUser=userRepository.save(toCreate);
        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserEntity getUserById(Long userId) throws ResourceNotFoundException {
        return  userRepository.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("no user with the id  "+userId+" exists")
        );
    }

    public UserEntity save(UserEntity newUser) {
        return userRepository.save(newUser);
    }
}
