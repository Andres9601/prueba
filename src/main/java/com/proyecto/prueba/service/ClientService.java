package com.proyecto.prueba.service;

import com.proyecto.prueba.model.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for performing operations related to clients.
 */
@Service
public interface ClientService {


    /**
     * Retrieves a list of clients.
     *
     * @return List of ClientDTO objects representing the clients.
     * @throws Exception if an error occurs while retrieving the clients.
     */
    @Qualifier(value = "ClientServiceImpl")
    List<ClientDTO> findClients() throws Exception;


    /**
     * Saves a new client.
     *
     * @param clientDTO The ClientDTO object representing the client to be saved.
     * @return A message indicating the result of the save operation.
     * @throws IllegalAccessException if the save operation is not allowed.
     */
    @Qualifier(value = "ClientServiceImpl")
    String saveClient (ClientDTO clientDTO) throws IllegalAccessException;


    /**
     * Updates an existing client.
     *
     * @param clientDTO The ClientDTO object representing the client to be updated.
     * @return A message indicating the result of the update operation.
     * @throws IllegalAccessException if the update operation is not allowed.
     */
    @Qualifier(value = "ClientServiceImpl")
    String updateClient (ClientDTO clientDTO) throws IllegalAccessException;


    /**
     * Deletes a client by identification.
     *
     * @param identification The identification of the client to be deleted.
     * @return A message indicating the result of the delete operation.
     */
    @Qualifier(value = "ClientServiceImpl")
    String deleteClient (Long identification);

}
