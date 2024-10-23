package com.JWTCurso.security.jwt;

import com.JWTCurso.security.usuario.ShopUsuarioDetalleService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class AuthTokenFilter extends OncePerRequestFilter {
    private JwtUtils jwtUtils;
    private ShopUsuarioDetalleService usuarioDetalleService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = parseJwt(request);
            if (StringUtils.hasText(jwt) && jwtUtils.validarToken(jwt)) {
                String username = jwtUtils.getUsuarioDelToken(jwt);
                UserDetails usuarioDetalle = usuarioDetalleService.loadUserByUsername(username);
                var auth = new UsernamePasswordAuthenticationToken(usuarioDetalle, null, usuarioDetalle.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }catch (JwtException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(e.getMessage()+": Token invalido o expirado, por favor intente denuevo");
        }catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().write(e.getMessage());
        }
        filterChain.doFilter(request, response);

    }

    private String parseJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader("Authorization");
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            //Devuelve el token sin el Bearer
            return headerAuth.substring(7);
        }
        return null;
    }
}
