package com.proyecto.prueba.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.proyecto.prueba.model.dto.ClientDTO;
import com.proyecto.prueba.model.dto.LoanDTO;
import com.proyecto.prueba.service.LoanService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan/")
public class LoanController {

    @Autowired
    LoanService loanService;
    private static final Logger logger = LogManager.getLogger(LoanController.class);

    @GetMapping(path= "findLoans", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findLoans() {
        try {
            return ResponseEntity.ok(loanService.findLoans());
        } catch (Exception e) {
            logger.error("error en {} ", LoanController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    @PostMapping(path = "save", consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveLoan(@RequestBody LoanDTO loanDTO) {
        try {
            return ResponseEntity.ok(loanService.saveLoan(loanDTO));
        } catch (Exception e) {
            logger.error("error en {} ", LoanController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }
    }

}
