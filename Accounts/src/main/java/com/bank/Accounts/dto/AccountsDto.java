package com.bank.Accounts.dto;


import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;

@Data
public class AccountsDto {


    @NotEmpty
    private Long accountNumber;

    @NotEmpty(message =  "cannot be null or empty")
    private String accountType;

    @NotEmpty(message = "Cannot be null or empty")
    private String branchAddress;
}
