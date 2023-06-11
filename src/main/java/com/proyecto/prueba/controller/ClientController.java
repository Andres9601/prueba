package com.proyecto.prueba.controller;

import com.proyecto.prueba.model.dto.ClientDTO;
import com.proyecto.prueba.service.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * The controller class for the Client database table.
 *
 */

@RestController
@RequestMapping("/api/client/")
@SecurityRequirement(name = "bearerAuth")
public class ClientController {

    private static final Logger logger = LogManager.getLogger(ClientController.class);

    @Autowired
    ClientService clientService;


    /**
     * Endpoint to retrieve all clients.
     *
     * @return ResponseEntity with the list of clients in JSON format.
     */
    @GetMapping(path = "findClients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findClients() {
        try {
            return ResponseEntity.ok(clientService.findClients());
        } catch (Exception e) {
            logger.error("error en {} ", ClientController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Endpoint to save a new client.
     *
     * @param clientDTO The ClientDTO object representing the client to be saved.
     * @return ResponseEntity with a success message on success, or an error message on failure.
     */
    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveclient(@RequestBody ClientDTO clientDTO) {
        try {
            return ResponseEntity.ok(clientService.saveClient(clientDTO));
        } catch (Exception e) {
            logger.error("error en {} ", ClientController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }
    }

    /**
     * Endpoint to update an existing client.
     *
     * @param clientDTO The ClientDTO object representing the client to be updated.
     * @return ResponseEntity with a success message on success, or an error message on failure.
     */
    @PutMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateClient(@RequestBody ClientDTO clientDTO) {
        try {
            return ResponseEntity.ok(clientService.updateClient(clientDTO));

        } catch (Exception e) {
            logger.error("error en {} ", ClientController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }
    }

    /**
     * Endpoint to delete a client.
     *
     * @param identification The identification ID of the client to be deleted.
     * @return ResponseEntity with a success message on success, or an error message on failure.
     */
    @DeleteMapping(path = "delete")
    public ResponseEntity<String> deleteClient(@RequestParam("identification") Long identification) {
        try {
            return ResponseEntity.ok(clientService.deleteClient(identification));
        } catch (Exception e) {
            logger.error("error en {} ", ClientController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getCause() + e.getMessage());
        }

    }
}
