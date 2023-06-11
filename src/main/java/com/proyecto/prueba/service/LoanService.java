package com.proyecto.prueba.service;

import com.proyecto.prueba.model.dto.ClientDTO;
import com.proyecto.prueba.model.dto.LoanDTO;
import com.proyecto.prueba.model.dto.NewLoanDTO;
import com.proyecto.prueba.model.dto.PayInstallmentsDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanService {

    @Qualifier(value = "LoanServiceImpl")
    List<LoanDTO> findLoans() throws Exception;

    @Qualifier(value = "LoanServiceImpl")
    String saveLoan (NewLoanDTO loanDTO) throws IllegalAccessException;

    @Qualifier(value = "LoanServiceImpl")
    String updateLoan (LoanDTO loanDTO) throws IllegalAccessException;

    @Qualifier(value = "LoanServiceImpl")
    String deleteLoan (Long idLoan);

    @Qualifier(value = "LoanServiceImpl")
    String payIntallments (PayInstallmentsDTO payInstallmentsDTO) throws IllegalAccessException;

}
