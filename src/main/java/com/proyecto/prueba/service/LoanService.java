package com.proyecto.prueba.service;

import com.proyecto.prueba.model.dto.LoanDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LoanService {

    @Qualifier(value = "LoanServiceImpl")
    List<LoanDTO> findLoans();

    @Qualifier(value = "LoanServiceImpl")
    String saveLoan (LoanDTO loanDTO) throws IllegalAccessException;
}
