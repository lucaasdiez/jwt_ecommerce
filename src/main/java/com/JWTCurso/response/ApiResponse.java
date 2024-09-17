package com.JWTCurso.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class ApiResponse {
    private String mensaje;
    private Object data;
}
