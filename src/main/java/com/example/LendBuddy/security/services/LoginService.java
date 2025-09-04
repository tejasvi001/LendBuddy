package com.example.LendBuddy.security.services;

import com.example.LendBuddy.dtos.LoginDTO;
import com.example.LendBuddy.dtos.LoginResponseDTO;
import com.example.LendBuddy.entities.UserEntity;
import com.example.LendBuddy.exceptions.ResourceNotFoundException;
import com.example.LendBuddy.services.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final UserService userService;
//    private final SessionService sessionService;
    public LoginService(AuthenticationManager authenticationManager, JWTService jwtService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.userService = userService;
//        this.sessionService = sessionService;
    }
    //     JWT tokens-2 tokens AT and RT
    public LoginResponseDTO login(LoginDTO loginDTO) {
        Authentication authentication=authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(),loginDTO.getPassword())
        );

        UserEntity user=(UserEntity) authentication.getPrincipal();
        String accessToken= jwtService.generateAccessToken(user);
        String refreshToken= jwtService.generateRefreshToken(user);
        return  new LoginResponseDTO(user.getId(), accessToken,refreshToken);
    }

    public LoginResponseDTO refreshToken(String refreshToken)  {
        Long userId= jwtService.getUserIdFromToken(refreshToken);
        UserEntity user= null;
        try {
            user = userService.getUserById(userId);
        } catch (ResourceNotFoundException e) {
            throw new RuntimeException(e);
        }
        String accessToken= jwtService.generateAccessToken(user);
        return  new LoginResponseDTO(user.getId(), accessToken,refreshToken);

    }
}
