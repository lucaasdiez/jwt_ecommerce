package com.JWTCurso.controller;

import com.JWTCurso.dto.JwtDTO;
import com.JWTCurso.dto.UsuarioDTO;
import com.JWTCurso.response.ApiResponse;
import com.JWTCurso.service.auth.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@Valid @RequestBody UsuarioDTO usuario) {
        try {
            JwtDTO jwtDTO = authService.login(usuario);
            return ResponseEntity.ok(new ApiResponse("Login Ok", jwtDTO));
        }catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
