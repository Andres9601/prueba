package com.proyecto.prueba.Exceptions;


import java.io.Serial;

public class ExceptionsClass extends RuntimeException {

    /**
     *
     */
    @Serial
    private static final long serialVersionUID = 1L;

	public ExceptionsClass (String message) {
        super (message);
    }

    public ExceptionsClass (Throwable cause) {
        super (cause);
    }

    public ExceptionsClass (String message, Throwable cause) {
        super (message, cause);
    }
}