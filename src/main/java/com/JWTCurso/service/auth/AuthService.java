package com.JWTCurso.service.auth;

import com.JWTCurso.dto.JwtDTO;
import com.JWTCurso.dto.UsuarioDTO;

public interface AuthService {
    JwtDTO login(UsuarioDTO usuario);
    UsuarioDTO registrar(UsuarioDTO usuario);
}
