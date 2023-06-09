package com.proyecto.prueba.model.dto;

import com.proyecto.prueba.model.entity.Loan;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {

    private Long identification;

    private String clientType;

    private String name;

    private Date birthDate;

    private String gender;

    private Long accountNumber;

    private String accountType;

    private String bankingEntityName;

    private Date contractStartDate;

    private String classification;

    private Set<Loan> userLoans;

    public Long getIdentification() {
        return identification;
    }

    public void setIdentification(Long identification) {
        this.identification = identification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Long getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(Long accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getBankingEntityName() {
        return bankingEntityName;
    }

    public void setBankingEntityName(String bankingEntityName) {
        this.bankingEntityName = bankingEntityName;
    }

    public Date getContractStartDate() {
        return contractStartDate;
    }

    public void setContractStartDate(Date contractStartDate) {
        this.contractStartDate = contractStartDate;
    }

    public String getClassification() {
        return classification;
    }

    public void setClassification(String classification) {
        this.classification = classification;
    }

    public Set<Loan> getUserLoans() {
        return userLoans;
    }

    public void setUserLoans(Set<Loan> userLoans) {
        this.userLoans = userLoans;
    }

    public String getClientType() {
        return clientType;
    }

    public void setClientType(String clientType) {
        this.clientType = clientType;
    }
}