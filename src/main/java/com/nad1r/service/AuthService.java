package com.nad1r.service;

import com.nad1r.exception.WrongCredentialsException;
import com.nad1r.repository.RetweetRepository;
import com.nad1r.request.AuthRequest;
import com.nad1r.request.RegisterRequest;
import com.nad1r.response.TokenResponse;
import com.nad1r.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;

    private final UserService userService;
    private final TokenService tokenService;
    private final ModelMapper modelMapper;
    private final RetweetRepository retweetRepository;

    public TokenResponse login(AuthRequest request){
        try{
            Authentication auth = authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
            return TokenResponse
                    .builder()
                    .accessToken(tokenService.generateToken(auth))
                    .userId(userService.findUserByUsername(request.getUsername()).getId())
                    .username(request.getUsername())
                    .build();
        }catch (final BadCredentialsException exp){
            throw new WrongCredentialsException("Invalid Username and Password!");
        }
    }

    public UserResponse signup(RegisterRequest request){
        return modelMapper.map(userService.create(request), UserResponse.class);
    }
}
