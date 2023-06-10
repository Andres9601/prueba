package com.proyecto.prueba.controller;

import com.proyecto.prueba.model.dto.ClientDTO;
import com.proyecto.prueba.service.ClientService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/client/")
public class ClientController {

    private static final Logger logger = LogManager.getLogger(ClientController.class);

    @Autowired
    ClientService clientService;

    @GetMapping(path = "findClients", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findClients() {
        try {
            return ResponseEntity.ok(clientService.findClients());
        } catch (Exception e) {
            logger.error("error en {} ", ClientController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveclient(@RequestBody ClientDTO clientDTO) {
        try {
            return ResponseEntity.ok(clientService.saveClient(clientDTO));
        } catch (Exception e) {
            logger.error("error en {} ", ClientController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }
    }

    @PutMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateClient(@RequestBody ClientDTO clientDTO) {
        try {
            return ResponseEntity.ok(clientService.updateClient(clientDTO));

        } catch (Exception e) {
            logger.error("error en {} ", ClientController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }
    }

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
