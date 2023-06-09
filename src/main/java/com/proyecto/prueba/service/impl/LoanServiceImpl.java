package com.proyecto.prueba.service.impl;

import com.proyecto.prueba.model.dto.LoanDTO;
import com.proyecto.prueba.model.entity.Loan;
import com.proyecto.prueba.repository.LoanRepository;
import com.proyecto.prueba.service.LoanService;
import com.proyecto.prueba.service.UtilsOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanServiceImpl implements LoanService {

    private static final Logger logger = LogManager.getLogger(LoanServiceImpl.class);


    @Autowired
    LoanRepository loanRepository;

    @Autowired
    UtilsOperations utilsOperations;

    @Override
    public List<LoanDTO> findLoans() {

        List<Loan> loans = loanRepository.findAll();
        List<LoanDTO> loansDTO = loans.stream()
                .map(loan -> {
                    LoanDTO loanDTO = new LoanDTO();
                    try {
                        utilsOperations.copyFields(loan, loanDTO);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                    return loanDTO;
                })
                .toList();

            return loansDTO;
    }

    @Override
    public String saveLoan(LoanDTO loanDTO) throws IllegalAccessException {
        Loan loan =new Loan();
        utilsOperations.copyFields(loanDTO,loan);
        loanRepository.save(loan);
        return "Usuario guardado con Exito";
    }
}
