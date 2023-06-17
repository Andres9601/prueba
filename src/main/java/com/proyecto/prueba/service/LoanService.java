package com.proyecto.prueba.service;

import com.proyecto.prueba.Exceptions.ExceptionsClass;
import com.proyecto.prueba.model.dto.LoanDTO;
import com.proyecto.prueba.model.dto.NewLoanDTO;
import com.proyecto.prueba.model.dto.PayInstallmentsDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service interface for performing operations related to loans.
 */
@Service
public interface LoanService {

    /**
     * Retrieves a list of loans.
     *
     * @return List of LoanDTO objects representing the loans.
     * @throws Exception if an error occurs while retrieving the loans.
     */
    @Qualifier(value = "LoanServiceImpl")
    List<LoanDTO> findLoans() throws ExceptionsClass;


    /**
     * Saves a new loan.
     *
     * @param loanDTO The NewLoanDTO object representing the loan to be saved.
     * @return A message indicating the result of the save operation.
     * @throws IllegalAccessException if the save operation is not allowed.
     */
    @Qualifier(value = "LoanServiceImpl")
    String saveLoan (NewLoanDTO loanDTO) throws IllegalAccessException;


    /**
     * Updates an existing loan.
     *
     * @param loanDTO The NewLoanDTO object representing the loan to be updated.
     * @return A message indicating the result of the update operation.
     * @throws IllegalAccessException if the update operation is not allowed.
     */
    @Qualifier(value = "LoanServiceImpl")
    String updateLoan (NewLoanDTO loanDTO) throws IllegalAccessException;


    /**
     * Deletes a loan by ID.
     *
     * @param idLoan The ID of the loan to be deleted.
     * @return A message indicating the result of the delete operation.
     */
    @Qualifier(value = "LoanServiceImpl")
    String deleteLoan (Long idLoan);


    /**
     * Pays the installments of a loan.
     *
     * @param payInstallmentsDTO The PayInstallmentsDTO object representing the installment payment details.
     * @return A message indicating the result of the installment payment.
     * @throws IllegalAccessException if the payment operation is not allowed.
     */
    @Qualifier(value = "LoanServiceImpl")
    String payIntallments (PayInstallmentsDTO payInstallmentsDTO) throws IllegalAccessException;

}
