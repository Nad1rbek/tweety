package com.nad1r.controller;

import com.nad1r.request.AuthRequest;
import com.nad1r.request.RegisterRequest;
import com.nad1r.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> handleLogin(@RequestBody AuthRequest request){
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/singup")
    public ResponseEntity<?> handleSignUp(@Valid @RequestBody RegisterRequest request){
        return ResponseEntity.ok(authService.signup(request));
    }
}
