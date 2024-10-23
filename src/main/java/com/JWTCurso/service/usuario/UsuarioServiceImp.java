package com.JWTCurso.service.usuario;

import com.JWTCurso.dto.UsuarioDTO;
import com.JWTCurso.exeptions.AlreadyExistExeption;
import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Usuario;
import com.JWTCurso.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImp implements UsuarioService{
    private final UsuarioRepository usuarioRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Override
    public Usuario createUsuario(UsuarioDTO usuarioDTO) {
        return Optional.of(usuarioDTO)
                .filter(usuario -> !usuarioRepository.existsByEmail(usuarioDTO.getEmail()))
                .map(usuariodto ->{
                    Usuario usuario = new Usuario();
                    usuario.setEmail(usuarioDTO.getEmail());
                    usuario.setNombre(usuarioDTO.getNombre());
                    usuario.setApellido(usuarioDTO.getApellido());
                    usuario.setPassword(passwordEncoder.encode(usuarioDTO.getPassword()));
                    return usuarioRepository.save(usuario);
                }).orElseThrow(() -> new AlreadyExistExeption("Usuario con email" + usuarioDTO.getEmail() +"ya existe"));
    }

    @Override
    public Usuario updateUsuario(UsuarioDTO usuarioDTO, Integer id) {
        return usuarioRepository.findById(id).map(usuarioExistente ->{
            usuarioExistente.setNombre(usuarioDTO.getNombre());
            usuarioExistente.setApellido(usuarioDTO.getApellido());
            usuarioExistente.setEmail(usuarioDTO.getEmail());
            return usuarioRepository.save(usuarioExistente);
        }).orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    @Override
    public void deleteUsuario(Integer id) {
        usuarioRepository.findById(id).ifPresentOrElse(usuarioRepository :: delete, () -> {
            throw new ResourceNotFoundException("Usuario no encontrado");
        });
    }

    @Override
    public UsuarioDTO convertirUsuarioADTO(Usuario usuario) {
        return modelMapper.map(usuario, UsuarioDTO.class);
    }

    @Override
    public Usuario getUsuarioAutenticado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        return usuarioRepository.findByEmail(email);
    }
}
