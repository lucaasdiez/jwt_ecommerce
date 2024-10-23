package com.JWTCurso.controller;

import com.JWTCurso.dto.UsuarioDTO;
import com.JWTCurso.exeptions.AlreadyExistExeption;
import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Usuario;
import com.JWTCurso.response.ApiResponse;
import com.JWTCurso.service.usuario.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/usuarios")
public class UsuarioController {
    private final UsuarioService usuarioService;

    @GetMapping("/{usuarioId}/usuario")
    public ResponseEntity<ApiResponse> getUsuarioById(@PathVariable Integer usuarioId) {
        try {
            Usuario usuario = usuarioService.getUsuarioById(usuarioId);
            UsuarioDTO usuarioDTO = usuarioService.convertirUsuarioADTO(usuario);
            return ResponseEntity.ok(new ApiResponse("Usuario encontrado", usuarioDTO));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/agregar")
    public ResponseEntity<ApiResponse> agregarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.createUsuario(usuarioDTO);
            UsuarioDTO usuarioDTOAgregado = usuarioService.convertirUsuarioADTO(usuario);
            return ResponseEntity.ok(new ApiResponse("Usuario agregado", usuarioDTOAgregado));
        }catch (AlreadyExistExeption e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PutMapping("/{id}/update")
    public ResponseEntity<ApiResponse> updateUsuario(@PathVariable Integer id, @RequestBody UsuarioDTO usuarioDTO) {
        try {
            Usuario usuario = usuarioService.updateUsuario(usuarioDTO, id);
            UsuarioDTO usuarioDTOAgregado = usuarioService.convertirUsuarioADTO(usuario);
            return ResponseEntity.ok(new ApiResponse("Usuario actualizado", usuarioDTOAgregado));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }


    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<ApiResponse> eliminarUsuario(@PathVariable Integer id) {
        try {
            usuarioService.deleteUsuario(id);
            return ResponseEntity.ok(new ApiResponse("Usuario eliminado", null));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
