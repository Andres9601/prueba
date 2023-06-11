package com.proyecto.prueba.model.entity;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

/**
 * Custom implementation of the GrantedAuthority interface representing an authority/role.
 */
@AllArgsConstructor
public class Authority  implements GrantedAuthority {
    private String authority;


    /**
     * Returns the authority/role name.
     *
     * @return The authority/role name.
     */
    @Override
    public String getAuthority() {
        return this.authority;
    }
}