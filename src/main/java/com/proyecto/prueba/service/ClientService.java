package com.proyecto.prueba.service;

import com.proyecto.prueba.model.dto.ClientDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ClientService {

    @Qualifier(value = "ClientServiceImpl")
    List<ClientDTO> findClients() throws Exception;

    @Qualifier(value = "ClientServiceImpl")
    String saveClient (ClientDTO clientDTO) throws IllegalAccessException;

    @Qualifier(value = "ClientServiceImpl")
    String updateClient (ClientDTO clientDTO) throws IllegalAccessException;

    @Qualifier(value = "ClientServiceImpl")
    String deleteClient (Long identification);

}
