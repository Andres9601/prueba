package com.proyecto.prueba.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public interface UtilsOperations {

    @Qualifier(value = "UtilsOperationsImpl")
    public void copyFields(Object origin, Object destination) throws IllegalAccessException;

}
