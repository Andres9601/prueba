package com.proyecto.prueba.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Usuario_Rol")
@AllArgsConstructor
@NoArgsConstructor
public class UserRol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUserRol;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Usuario")
    @JsonIgnore
    private User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "rol")
    @JsonIgnore
    private Rol rol;


    public Long getIdUserRol() {
        return idUserRol;
    }

    public void setIdUserRol(Long idUserRol) {
        this.idUserRol = idUserRol;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }
}



