package com.proyecto.prueba.service.impl;

import com.proyecto.prueba.Exceptions.ExceptionsClass;
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

/**
 * Implementation of the ClientService interface.
 */
@Service
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = LogManager.getLogger(ClientServiceImpl.class);

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UtilsOperations utilsOperations;

    @Override
    public List<ClientDTO> findClients() throws Exception {
        logger.info("Buscando Cleintes...");
        List<Client> clients = clientRepository.findAll();
        List<ClientDTO> clientsDTO = clients.stream()
                .map(client -> {
                    ClientDTO clientDTO = new ClientDTO();
                    try {
                        utilsOperations.copyFields(client, clientDTO);
                    } catch (IllegalAccessException e) {
                        throw new ExceptionsClass(e.getMessage());
                    }
                    return clientDTO;
                })
                .toList();

        if (!clientsDTO.isEmpty()) {
            logger.info(" Se retornan los clientes encontrados");
            return clientsDTO;
        } else {
            throw new ExceptionsClass(" Busqueda vacia");
        }
    }

    @Override
    public String saveClient(ClientDTO clientDTO) throws IllegalAccessException {
        Client client = new Client();
        logger.info("Realizando validaciones");
        if (clientDTO.getIdentification() == null || clientDTO.getClientType() == null ||
                clientDTO.getBirthDate() == null || clientDTO.getGender() == null || clientDTO.getAccountNumber() == null ||
                clientDTO.getAccountType() == null || clientDTO.getBankingEntityName() == null) {
            throw new IllegalArgumentException("Se requiere un ClientDTO con identification, clientType, birthDate, " +
                    "gender, accountNumber, accountType, bankingEntityName y contractStartDate si és Employee, para guardar el cliente");
        }
        if (clientDTO.getClientType().equals("Employee") && clientDTO.getContractStartDate() == null) {
            throw new IllegalArgumentException("Se requiere un  contractStartDate si és Employee, para guardar el cliente");
        }
        clientDTO.setActive(true);
        Optional<Client> clientTemp = clientRepository.findById(clientDTO.getIdentification());
        if (clientDTO.getIdentification() != null && clientTemp.isEmpty()) {
            setClassification(clientDTO);
        } else {
            throw new IllegalArgumentException(" El usuario ya existe en el sistema");
        }
        logger.info("Copiando DTO a Entity");
        utilsOperations.copyFields(clientDTO, client);
        logger.info(" Guardando en base de datos el cliente encontrado ");
        clientRepository.save(client);
        return "Usuario guardado con Exito";
    }

    @Override
    public String updateClient(ClientDTO clientDTO) throws IllegalAccessException {
        logger.info("Realizando validaciones");
        if (clientDTO.getIdentification() == null || clientDTO.getClientType() == null ||
                clientDTO.getBirthDate() == null || clientDTO.getGender() == null || clientDTO.getAccountNumber() == null ||
                clientDTO.getAccountType() == null || clientDTO.getBankingEntityName() == null) {
            throw new IllegalArgumentException("Se requiere un ClientDTO con identification, clientType, birthDate, " +
                    "gender, accountNumber, accountType, bankingEntityName y contractStartDate si és Employee, para actualizar el cliente");
        }
        if (clientDTO.getClientType().equals("Employee") && clientDTO.getContractStartDate() == null) {
            throw new IllegalArgumentException("Se requiere un  contractStartDate si és Employee, para actualizar el cliente");
        }
        logger.info(" Buscando cliente con ese numero de identificacion");
        Optional<Client> clientTemp = clientRepository.findById(clientDTO.getIdentification());
        if (clientTemp.isPresent()) {
            logger.info(" Pasando cambios al cliente encontrado");

            Client client = clientTemp.get();
            setClassification(clientDTO);
            logger.info("Copiando DTO a Entity");
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

    public void setClassification(ClientDTO clientDTO) {
        switch (clientDTO.getClientType()) {
            case "Employee" -> {
                Period period = Period.between(clientDTO.getContractStartDate()
                        .toInstant()
                        .atZone(ZoneId.systemDefault()).toLocalDate(), LocalDate.now());
                if (period.getYears() < 1) {
                    clientDTO.setClassification("A");
                } else if (period.getYears() == 1) {
                    clientDTO.setClassification("B");
                } else {
                    clientDTO.setClassification("C");
                }

            }
            case "Independent" -> {
                clientDTO.setContractStartDate(null);
                clientDTO.setClassification("A");
            }
            default -> throw new IllegalArgumentException(" El tipo de cliente debe ser Employee o Independent");
        }
    }
}
