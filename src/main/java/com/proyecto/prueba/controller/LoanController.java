package com.proyecto.prueba.controller;

import com.proyecto.prueba.model.dto.NewLoanDTO;
import com.proyecto.prueba.model.dto.PayInstallmentsDTO;
import com.proyecto.prueba.service.LoanService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan/")
@SecurityRequirement(name = "bearerAuth")
public class LoanController {

    @Autowired
    LoanService loanService;
    private static final Logger logger = LogManager.getLogger(LoanController.class);

    /**
     * Endpoint to retrieve all loans.
     *
     * @return ResponseEntity with the list of loans in JSON format.
     */
    @GetMapping(path= "findLoans", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findLoans() {
        try {
            return ResponseEntity.ok(loanService.findLoans());
        } catch (Exception e) {
            logger.error("error en {} ", LoanController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }


    /**
     * Endpoint to save a new loan.
     *
     * @param loanDTO The NewLoanDTO object representing the loan to be saved.
     * @return ResponseEntity with a success message on success, or an error message on failure.
     */
    @PostMapping(path = "save", consumes =  MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> saveLoan(@RequestBody NewLoanDTO loanDTO) {
        try {
            return ResponseEntity.ok(loanService.saveLoan(loanDTO));
        } catch (Exception e) {
            logger.error("error en {} ", LoanController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }
    }

    /**
     * Endpoint to update an existing loan.
     *
     * @param loanDTO The NewLoanDTO object representing the loan to be updated.
     * @return ResponseEntity with a success message on success, or an error message on failure.
     */
    @PutMapping(path = "update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> updateLoan(@RequestBody NewLoanDTO loanDTO) {
        try {
            return ResponseEntity.ok(loanService.updateLoan(loanDTO));

        } catch (Exception e) {
            logger.error("error en {} ", LoanController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }
    }

    /**
     * Endpoint to delete a loan.
     *
     * @param idLoan The ID of the loan to be deleted.
     * @return ResponseEntity with a success message on success, or an error message on failure.
     */
    @DeleteMapping(path = "delete")
    public ResponseEntity<String> deleteLoan(@RequestParam("idLoan") Long idLoan) {
        try {
            return ResponseEntity.ok(loanService.deleteLoan(idLoan));
        } catch (Exception e) {
            logger.error("error en {} ", LoanController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getCause() + e.getMessage());
        }

    }

    /**
     * Endpoint to pay installments of a loan.
     *
     * @param payInstallmentsDTO The PayInstallmentsDTO object representing the payment details.
     * @return ResponseEntity with a success message on success, or an error message on failure.
     */
    @PutMapping(path = "payIntallments", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> payIntallments(@RequestBody PayInstallmentsDTO payInstallmentsDTO) {
        try {
            return ResponseEntity.ok(loanService.payIntallments(payInstallmentsDTO));

        } catch (Exception e) {
            logger.error("error en {} ", LoanController.class + e.getMessage());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(e.getMessage());
        }
    }

}
