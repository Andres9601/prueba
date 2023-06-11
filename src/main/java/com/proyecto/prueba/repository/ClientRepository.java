package com.proyecto.prueba.repository;

import com.proyecto.prueba.model.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for performing CRUD operations on the Client entity.
 */
@Repository
public interface ClientRepository extends JpaRepository<Client,Long> {
}
