package com.JWTCurso.service.auth;

import com.JWTCurso.dto.JwtDTO;
import com.JWTCurso.dto.UsuarioDTO;
import com.JWTCurso.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuthServiceImp implements AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @Override
    public JwtDTO login(UsuarioDTO usuario) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(
                        usuario.getEmail(), usuario.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generarTokenParaUsuario(authentication);
        return new JwtDTO(usuario.getId(), jwt);
    }

    @Override
    public UsuarioDTO registrar(UsuarioDTO usuario) {
        return null;
    }
}
