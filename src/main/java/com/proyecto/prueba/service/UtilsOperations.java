package com.proyecto.prueba.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service interface for performing utility operations.
 */
@Service
public interface UtilsOperations {


    /**
     * Copies the fields from the origin object to the destination object.
     *
     * @param origin      The source object from which the fields will be copied.
     * @param destination The destination object where the fields will be copied to.
     * @throws IllegalAccessException if the operation is not allowed.
     */
    @Qualifier(value = "UtilsOperationsImpl")
    public void copyFields(Object origin, Object destination) throws IllegalAccessException;

}
