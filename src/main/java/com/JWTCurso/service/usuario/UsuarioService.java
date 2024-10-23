package com.JWTCurso.service.usuario;

import com.JWTCurso.dto.UsuarioDTO;
import com.JWTCurso.model.Usuario;

public interface UsuarioService {
    Usuario getUsuarioById(Integer id);
    Usuario createUsuario(UsuarioDTO usuarioDTO);
    Usuario updateUsuario(UsuarioDTO usuarioDTO, Integer id);
    void deleteUsuario(Integer id);
    UsuarioDTO convertirUsuarioADTO(Usuario usuario);

    Usuario getUsuarioAutenticado();
}
