package com.proyecto.prueba.Exceptions;

import com.proyecto.prueba.model.dto.ExceptionResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class HandlerExceptions {

    @ExceptionHandler(ExceptionsClass.class)
    public ResponseEntity<ExceptionResponseDTO> handle(ExceptionsClass runtime) {
        //runtime.printStackTrace();
        return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(ExceptionResponseDTO.builder()
                .status(HttpStatus.PRECONDITION_FAILED.value()).error(runtime.getMessage()).build());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ExceptionResponseDTO> handleMissingServletRequestParameterException(
            MissingServletRequestParameterException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDTO.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("No se puede procesar tu solicitud, por favor revisa los parametros")
                        .build());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseDTO> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDTO.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Error de formato JSON en el cuerpo de la solicitud.")
                        .build());
    }


    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionResponseDTO> handleHttpMessageNotReadableException(
            HttpMediaTypeNotSupportedException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ExceptionResponseDTO.builder()
                        .status(HttpStatus.BAD_REQUEST.value())
                        .error("Error de formato JSON en el cuerpo de la solicitud.")
                        .build());
    }
}
