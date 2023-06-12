package com.proyecto.prueba.service.impl;

import com.proyecto.prueba.model.dto.LoanDTO;
import com.proyecto.prueba.model.dto.NewLoanDTO;
import com.proyecto.prueba.model.dto.PayInstallmentsDTO;
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
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the LoanService interface.
 */
@Service
public class LoanServiceImpl implements LoanService {

    private static final Logger logger = LogManager.getLogger(LoanServiceImpl.class);


    @Autowired
    LoanRepository loanRepository;

    @Autowired
    ClientRepository clientRepository;

    @Autowired
    UtilsOperations utilsOperations;

    MathContext mathContext = new MathContext(6, RoundingMode.UP);

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
        Loan loan = new Loan();
        LoanDTO loanDTO = newloanDTO.getLoanDTO();
        Optional<Client> client = clientRepository.findById(newloanDTO.getClientId());
        if (client.isPresent()) {
            loanDTO.setInstallmentsPaid(0L);
            loanDTO.setClient(client.get());

            List<BigDecimal>  installments = calculateInstallments(loanDTO);
            BigDecimal addition = BigDecimal.valueOf(0);
            for (BigDecimal installment : installments
            ) {
                addition = addition.add(installment);
            }
            loanDTO.setInstallmentValue(addition.divide(BigDecimal.valueOf(loanDTO.getInstallments()), mathContext));
            loanDTO.setBalance(loanDTO.getValue());
            loanDTO.setCreateDate(new Date());
            loanDTO.setStatus("active");
            utilsOperations.copyFields(loanDTO, loan);
            loanRepository.save(loan);
            return "Loan guardado con Exito";
        } else {
            throw new IllegalArgumentException(" El cliente no existe en el sistema");
        }
    }

    @Override
    public String updateLoan(NewLoanDTO newloanDTO) throws IllegalAccessException {
        logger.info(" Buscando Loan con ese numero de identificacion");

        Optional<Loan> loanTemp = loanRepository.findById(newloanDTO.getLoanDTO().getIdLoan());
        if (loanTemp.isPresent()&&loanTemp.get().getInstallmentsPaid().equals(0L)) {
            Loan loan = loanTemp.get();
            LoanDTO loanDTO = newloanDTO.getLoanDTO();
            Optional<Client> client = clientRepository.findById(newloanDTO.getClientId());
            if (client.isPresent()) {
                loanDTO.setInstallmentsPaid(loan.getInstallmentsPaid());
                loanDTO.setClient(client.get());

                List<BigDecimal>  installments = calculateInstallments(loanDTO);

                BigDecimal addition = BigDecimal.valueOf(0);
                for (BigDecimal installment : installments
                ) {
                    addition = addition.add(installment);
                }
                loanDTO.setInstallmentValue(addition.divide(BigDecimal.valueOf(loanDTO.getInstallments()), mathContext));
                loanDTO.setBalance(loanDTO.getValue());
                loanDTO.setCreateDate(loan.getCreateDate());
                loanDTO.setStatus(loan.getStatus());
                utilsOperations.copyFields(loanDTO, loan);
                loanRepository.save(loan);
                return "Loan guardado con Exito";
            } else {
                throw new IllegalArgumentException(" El cliente no existe en el sistema");
            }
        } else {
            throw new IllegalArgumentException(" El loan no existe en el sistema o ya tiene cuotas pagadas");
        }
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

    @Override
    public String payIntallments(PayInstallmentsDTO payInstallmentsDTO) throws IllegalAccessException {
        Optional<Loan> loanTemp = loanRepository.findById(payInstallmentsDTO.getIdLoan());
        if (loanTemp.isPresent()) {
            LoanDTO loanDTO = new LoanDTO();
            utilsOperations.copyFields(loanTemp.get(), loanDTO);
            logger.info(" Loan encontrado");
            logger.info(" Pagando Cuotas al loan encontrado");
            if (!payInstallmentsDTO.getNumber().equals(0L) && payInstallmentsDTO.getNumber() <= (loanDTO.getInstallments() - loanDTO.getInstallmentsPaid())) {
                loanDTO.setInstallmentsPaid(loanDTO.getInstallmentsPaid() + payInstallmentsDTO.getNumber());
                BigDecimal partialInstallments = loanDTO.getValue().divide(BigDecimal.valueOf(loanDTO.getInstallments()), mathContext);
                loanDTO.setBalance(loanDTO.getBalance().subtract(partialInstallments.multiply(BigDecimal.valueOf(loanDTO.getInstallmentsPaid()))));
                if (loanDTO.getInstallmentsPaid().equals(loanDTO.getInstallments())) {
                    loanDTO.setStatus("closed");
                }
                Loan loan = new Loan();
                utilsOperations.copyFields(loanDTO, loan);
                loanRepository.save(loan);
                logger.info(" Loan actualizado con Exito");
                return "Loan actualizado con Exito";
            } else {
                throw new IllegalArgumentException(" No se pueden pagar ese numero de cuotas");
            }
        } else {
            throw new IllegalArgumentException(" El loan no existe en el sistema");
        }
    }

    private List<BigDecimal> calculateInstallments(LoanDTO loanDTO) {
        switch (loanDTO.getClient().getClassification()) {
            case "A":
                loanDTO.setInterestRate(BigDecimal.valueOf(3L));
                break;
            case "B":
                loanDTO.setInterestRate(BigDecimal.valueOf(2L));
                break;
            default:
                loanDTO.setInterestRate(BigDecimal.valueOf(1L));
        }
        Long additionalMonths;
        if (loanDTO.getInstallments() > 6) {
            additionalMonths = loanDTO.getInstallments() - 6L;
            loanDTO.setInterestRate(loanDTO.getInterestRate().add(BigDecimal.valueOf(0.1).multiply(BigDecimal.valueOf(additionalMonths))));
        }
        List<BigDecimal> installments = new ArrayList<>();
        for (int i = 0; i < loanDTO.getInstallments(); i++) {
            BigDecimal parcialInstallment = loanDTO.getValue().divide(BigDecimal.valueOf(loanDTO.getInstallments()), mathContext);
            BigDecimal totalValue = loanDTO.getValue().subtract(parcialInstallment.multiply(BigDecimal.valueOf(i)));
            BigDecimal interest = totalValue.multiply(loanDTO.getInterestRate().divide(BigDecimal.valueOf(100), mathContext));
            BigDecimal value = parcialInstallment.add(interest);
            installments.add(value);
        }
        return installments;
    }

}


