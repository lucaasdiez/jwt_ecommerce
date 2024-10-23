package com.JWTCurso.controller;

import com.JWTCurso.dto.OrdenDTO;
import com.JWTCurso.exeptions.ResourceNotFoundException;
import com.JWTCurso.model.Orden;
import com.JWTCurso.response.ApiResponse;
import com.JWTCurso.service.orden.OrdenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/ordenes")
public class OrdenController {
    private final OrdenService ordenService;

    @PostMapping("/orden")
    public ResponseEntity<ApiResponse> crearOrden(@RequestParam Integer idUsuario) {
        try {
            Orden orden = ordenService.crearOrden(idUsuario);
            return ResponseEntity.ok(new ApiResponse("Orden creada con exito", orden));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ApiResponse("Error al crear Orden", e.getMessage()));
        }
    }

    @GetMapping("/{idOrden}/orden")
    public ResponseEntity<ApiResponse> getOrdenById(@PathVariable Integer idOrden) {
        try {
            OrdenDTO ordenDTO = ordenService.getOrdenById(idOrden);
            return ResponseEntity.ok(new ApiResponse("Orden con exito", ordenDTO));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error al obtener el orden", e.getMessage()));
        }
    }

    @GetMapping("/{idUsuario}/orden")
    public ResponseEntity<ApiResponse> getOrdenByUsuario(@PathVariable Integer idUsuario) {
        try {
            List<OrdenDTO> ordenesDTO = ordenService.getOrdenesByUsuario(idUsuario);
            return ResponseEntity.ok(new ApiResponse("Ordenes con exito", ordenesDTO));
        }catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponse("Error al obtener el orden", e.getMessage()));
        }
    }

}
