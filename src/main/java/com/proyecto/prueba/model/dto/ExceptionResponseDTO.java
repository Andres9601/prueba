package com.proyecto.prueba.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ExceptionResponseDTO {
    private Integer status;
    private String error;

    public ExceptionResponseDTO(Integer status, String error) {
        this.status = status;
        this.error = error;
    }
}
