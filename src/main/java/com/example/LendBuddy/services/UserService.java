package com.example.LendBuddy.services;

import com.example.LendBuddy.dtos.SignUpDTO;
import com.example.LendBuddy.dtos.UserDTO;
import com.example.LendBuddy.entities.UserEntity;
import com.example.LendBuddy.exceptions.ResourceNotFoundException;
import com.example.LendBuddy.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public UserEntity getUserByEmail(String email) {
        log.debug("Fetching user by email={}", email);
        return userRepository.findByEmail(email).orElse(null);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("Loading user by username={}", username);
        return userRepository.findByEmail(username).orElseThrow(() -> {
            log.error("No user found with email={}", username);
            return new UsernameNotFoundException("No user with email " + username + " exists");
        });
    }

    public UserDTO signUp(SignUpDTO signUpDTO) {
        log.info("Attempting to register new user with email={}", signUpDTO.getEmail());

        Optional<UserEntity> existingUser = userRepository.findByEmail(signUpDTO.getEmail());
        if (existingUser.isPresent()) {
            log.warn("Signup failed: User already exists with email={}", signUpDTO.getEmail());
            throw new BadCredentialsException("User with email already exists " + signUpDTO.getEmail());
        }

        UserEntity toCreate = modelMapper.map(signUpDTO, UserEntity.class);
        toCreate.setPassword(passwordEncoder.encode(toCreate.getPassword()));

        UserEntity savedUser = userRepository.save(toCreate);
        log.info("User created successfully with id={} and email={}", savedUser.getId(), savedUser.getEmail());

        return modelMapper.map(savedUser, UserDTO.class);
    }

    public UserEntity getUserById(Long userId) throws ResourceNotFoundException {
        log.debug("Fetching user by id={}", userId);
        return userRepository.findById(userId).orElseThrow(() -> {
            log.error("User not found with id={}", userId);
            return new ResourceNotFoundException("No user with the id " + userId + " exists");
        });
    }

    public UserEntity save(UserEntity newUser) {
        log.info("Saving user with email={}", newUser.getEmail());
        return userRepository.save(newUser);
    }

    public UserDTO getUserProfile() {
        UserEntity authenticatedUser = getAuthenticatedUser();
        log.debug("Fetching profile for userId={}", authenticatedUser.getId());
        return modelMapper.map(authenticatedUser, UserDTO.class);
    }

    public UserDTO findUserById(Long id) {
        log.debug("Finding user DTO by id={}", id);
        return modelMapper.map(getUserById(id), UserDTO.class);
    }

    public UserDTO updateUser(Long id, Map<String, Object> updates) {
        log.info("Updating user with id={} and updates={}", id, updates.keySet());

        // Fetch entity
        UserEntity user = userRepository.findById(id).orElseThrow(() -> {
            log.error("User not found with id={}", id);
            return new ResourceNotFoundException("User not found with id " + id);
        });

        // Check if the authenticated user is the same as the one being updated
        UserEntity authenticatedUser = getAuthenticatedUser();
        if (!authenticatedUser.getId().equals(id)) {
            log.warn("Access denied: userId={} attempted to update userId={}", authenticatedUser.getId(), id);
            throw new AccessDeniedException("You are not authorized to update this user");
        }

        // Apply reflection-based updates
        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(UserEntity.class, key);
            if (field != null) {
                field.setAccessible(true);
                try {
                    ReflectionUtils.setField(field, user, value);
                    log.debug("Updated field={} with value={}", key, value);
                } catch (IllegalArgumentException e) {
                    log.error("Invalid type provided for field={} value={}", key, value, e);
                    throw new IllegalArgumentException("Invalid value for field " + key);
                }
            } else {
                log.warn("Attempted to update non-existing field '{}' on UserEntity", key);
            }
        });

        // Save updated user
        UserEntity savedUser = userRepository.save(user);
        log.info("User updated successfully with id={}", savedUser.getId());

        return modelMapper.map(savedUser, UserDTO.class);
    }

    private UserEntity getAuthenticatedUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof UserEntity)) {
            log.error("Authentication principal is not a valid UserEntity. Principal={}", principal);
            throw new IllegalStateException("Authentication principal is not a valid UserEntity");
        }

        UserEntity user = (UserEntity) principal;
        log.debug("Authenticated user: id={}, email={}", user.getId(), user.getEmail());
        return user;
    }
}
