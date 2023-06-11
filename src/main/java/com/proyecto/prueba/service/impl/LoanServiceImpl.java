package com.proyecto.prueba.service.impl;

import com.proyecto.prueba.model.dto.LoanDTO;
import com.proyecto.prueba.model.dto.NewLoanDTO;
import com.proyecto.prueba.model.entity.Client;
import com.proyecto.prueba.model.entity.Loan;
import com.proyecto.prueba.repository.ClientRepository;
import com.proyecto.prueba.repository.LoanRepository;
import com.proyecto.prueba.service.LoanService;
import com.proyecto.prueba.service.UtilsOperations;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class LoanServiceImpl implements LoanService {

    private static final Logger logger = LogManager.getLogger(LoanServiceImpl.class);


    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UtilsOperations utilsOperations;

    @Override
    public List<LoanDTO> findLoans() throws Exception {

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
        if (!loansDTO.isEmpty()) {
            logger.info(" Se retornan los loans encontrados");
            return loansDTO;
        } else {
            throw new Exception(" Busqueda vacia");
        }
    }

    @Override
    public String saveLoan(NewLoanDTO newloanDTO) throws IllegalAccessException {
        Loan loan =new Loan();
        LoanDTO loanDTO = newloanDTO.getLoanDTO();
        Optional<Client> client = clientRepository.findById(newloanDTO.getClientId());
        loanDTO.setClient(client.get());

        switch (client.get().getClassification()){
            case "A" :
                loanDTO.setInterestRate(BigDecimal.valueOf(3L));
                break;
            case "B" :
                loanDTO.setInterestRate(BigDecimal.valueOf(2L));
                break;
            default:
                loanDTO.setInterestRate(BigDecimal.valueOf(1L));
        }

        utilsOperations.copyFields(loanDTO,loan);
        loanRepository.save(loan);
        return "Loan guardado con Exito";
    }

    @Override
    public String updateLoan(LoanDTO loanDTO) throws IllegalAccessException {
        return null;
    }

    @Override
    public String deleteLoan(Long idLoan) {
        logger.info(" Buscando loan con ese numero de identificacion");
        Optional<Loan> loanTemp = loanRepository.findById(idLoan);
        if (loanTemp.isPresent()) {
            Client client = loanTemp.get().getClient();
            client.getUserLoans().remove(loanTemp.get());
            logger.info(" Loan encontrado");
            logger.info(" Eliminando el loan encontrado");
            loanRepository.deleteById(idLoan);
            logger.info(" Loan eliminado con Exito");
            return "Loan eliminado con Exito";

        } else {
            throw new IllegalArgumentException(" El loan no existe en el sistema");
        }
    }
}
