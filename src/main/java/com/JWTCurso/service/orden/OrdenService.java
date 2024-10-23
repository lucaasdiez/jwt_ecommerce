package com.JWTCurso.service.orden;

import com.JWTCurso.dto.OrdenDTO;
import com.JWTCurso.model.Orden;

import java.util.List;

public interface OrdenService {
    Orden crearOrden(Integer usuarioId);
    OrdenDTO getOrdenById(Integer id);
    OrdenDTO convertirOrdenADTO(Orden orden);
    List<OrdenDTO> getOrdenesByUsuario(Integer usuarioId);
}
