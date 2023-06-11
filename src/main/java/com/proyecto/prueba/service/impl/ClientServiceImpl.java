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

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UtilsOperations utilsOperations;

    @Override
    public List<ClientDTO> findClients() throws Exception {
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

        if (!clientsDTO.isEmpty()) {
            logger.info(" Se retornan los clientes encontrados");
            return clientsDTO;
        } else {
            throw new Exception(" Busqueda vacia");
        }
    }

    @Override
    public String saveClient(ClientDTO clientDTO) throws IllegalAccessException {
        Client client = new Client();
        clientDTO.setActive(true);
        Optional<Client> clientTemp = clientRepository.findById(clientDTO.getIdentification());
        if (clientDTO.getIdentification() != null && !clientTemp.isPresent()) {
            switch (clientDTO.getClientType()) {
                case "Employee":
                    Period period = Period.between(clientDTO.getContractStartDate()
                            .toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
                    if (period.getYears() < 1) {
                        clientDTO.setClassification("A");
                    } else {
                        switch (period.getYears()) {
                            case 1:
                                clientDTO.setClassification("B");
                                break;
                            default:
                                clientDTO.setClassification("C");
                        }
                    }
                    break;
                case "Independent":
                    clientDTO.setContractStartDate(null);
                    clientDTO.setClassification("A");
                    break;
                default:
                    throw new IllegalArgumentException(" El tipo de cliente debe ser Employee o Independent");
            }
        }else {
            throw new IllegalArgumentException(" El usuario ya existe en el sistema");
        }
        utilsOperations.copyFields(clientDTO,client);
        clientRepository.save(client);
        return "Usuario guardado con Exito";
    }

    @Override
    public String updateClient(ClientDTO clientDTO) throws IllegalAccessException {
        logger.info(" Buscando cliente con ese numero de identificacion");

        Optional<Client> clientTemp = clientRepository.findById(clientDTO.getIdentification());
        if (clientTemp.isPresent()) {
            logger.info(" Pasando cambios al cliente encontrado");

            Client client = clientTemp.get();
            switch (clientDTO.getClientType()) {
                case "Employee":
                    Period period = Period.between(clientDTO.getContractStartDate()
                            .toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
                    if (period.getYears() < 1) {
                        clientDTO.setClassification("A");
                    } else {
                        switch (period.getYears()) {
                            case 1:
                                clientDTO.setClassification("B");
                                break;
                            default:
                                clientDTO.setClassification("C");
                        }
                    }
                    break;
                case "Independent":
                    clientDTO.setContractStartDate(null);
                    clientDTO.setClassification("A");
                    break;
                default:
                    throw new IllegalArgumentException(" El tipo de cliente debe ser Employee o Independent");
            }
            utilsOperations.copyFields(clientDTO, client);
            logger.info(" Actualizando en base de datos el cliente encontrado ");
            clientRepository.save(client);
            logger.info(" Cliente actualizado con Exito");
            return "Usuario actualizado con Exito";
        } else {
            throw new IllegalArgumentException(" El usuario no existe en el sistema");
        }
    }

    @Override
    public String deleteClient(Long identification) {
        logger.info(" Buscando cliente con ese numero de identificacion");
        Optional<Client> clientTemp = clientRepository.findById(identification);
        if (clientTemp.isPresent()) {
            logger.info(" Cliente encontrado");
            logger.info(" Eliminando el cliente encontrado");
            clientRepository.deleteById(identification);
            logger.info(" Cliente eliminado con Exito");
            return "Usuario eliminado con Exito";

        } else {
            throw new IllegalArgumentException(" El usuario no existe en el sistema");
        }
    }


}
