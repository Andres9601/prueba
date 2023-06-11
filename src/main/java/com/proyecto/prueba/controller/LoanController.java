package com.proyecto.prueba.controller;

import com.proyecto.prueba.model.dto.ClientDTO;
import com.proyecto.prueba.model.dto.LoanDTO;
import com.proyecto.prueba.model.dto.NewLoanDTO;
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
    public ResponseEntity<String> saveLoan(@RequestBody NewLoanDTO loanDTO) {
        try {
            return ResponseEntity.ok(loanService.saveLoan(loanDTO));
        } catch (Exception e) {
            logger.error("error en {} ", LoanController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }
    }

    @PutMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateLoan(@RequestBody LoanDTO loanDTO) {
        try {
            return ResponseEntity.ok(loanService.updateLoan(loanDTO));

        } catch (Exception e) {
            logger.error("error en {} ", LoanController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "delete")
    public ResponseEntity<String> deleteLoan(@RequestParam("idLoan") Long idLoan) {
        try {
            return ResponseEntity.ok(loanService.deleteLoan(idLoan));
        } catch (Exception e) {
            logger.error("error en {} ", LoanController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getCause() + e.getMessage());
        }

    }
}
