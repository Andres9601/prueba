package com.proyecto.prueba.model.dto;

import com.proyecto.prueba.model.entity.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoanDTO {


    private Long idLoan;

    private Date createDate;

    private BigDecimal value;

    private BigDecimal interestRate;

    private String status;

    private Long installments;

    private Long installmentsPaid;

    private BigDecimal installmentValue;

    private Client client;


}
