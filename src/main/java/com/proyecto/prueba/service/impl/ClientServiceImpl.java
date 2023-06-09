package com.proyecto.prueba.service.impl;

import com.proyecto.prueba.model.dto.ClientDTO;
import com.proyecto.prueba.model.entity.Client;
import com.proyecto.prueba.repository.ClientRepository;
import com.proyecto.prueba.service.ClientService;
import com.proyecto.prueba.service.UtilsOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UtilsOperations utilsOperations;

    @Override
    public List<ClientDTO> findClients() {
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientsDTO = clients.stream()
                .map(client -> {
                    ClientDTO clientDTO = new ClientDTO();
                    try {
                        utilsOperations.copyFields(client, clientDTO);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    return clientDTO;
                })
                .toList();

        return clientsDTO;
    }

    @Override
    public String saveClient(ClientDTO clientDTO) throws IllegalAccessException {
        Client client =new Client();
        utilsOperations.copyFields(clientDTO,client);
        clientRepository.save(client);
        return "Usuario guardado con Exito";
    }
}
