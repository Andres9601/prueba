package com.proyecto.prueba.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewLoanDTO {


    private LoanDTO loanDTO;

    private Long clientId;


}
