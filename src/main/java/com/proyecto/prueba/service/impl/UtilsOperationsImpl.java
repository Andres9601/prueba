package com.proyecto.prueba.service.impl;

import com.proyecto.prueba.service.UtilsOperations;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;

/**
 * * The class of some utilities.
 *
 */

@Component
public class UtilsOperationsImpl implements UtilsOperations {

    /**
     * * Method for copy values of parameters between Entity and DTO classes.
     */

    public void copyFields(Object origin, Object destination) throws IllegalAccessException {
        Field[] fieldsOrigin = origin.getClass().getDeclaredFields();
        Field[] fieldsDestination = destination.getClass().getDeclaredFields();

        for (Field fieldOrigin : fieldsOrigin) {
            for (Field fieldDestination : fieldsDestination) {
                if (fieldOrigin.getName().equals(fieldDestination.getName())) {
                    fieldOrigin.setAccessible(true);
                    fieldDestination.setAccessible(true);
                    Object valor = fieldOrigin.get(origin);
                    fieldDestination.set(destination, valor);
                    break;
                }
            }
        }
    }


    /**
     * * Methods to pass empty input values to null.
     */

    public static String emptyToNull(String asValue) {
        if ( asValue != null ) {

            return asValue.equals("") || asValue.isEmpty() ? null : asValue;
        } else {
            return null;
        }
    }

    public static Long emptyToNullLong(Long asValue) {
        if ( asValue != null ) {

            return asValue.equals("")  ? null : asValue;
        } else {
            return null;
        }
    }


}
