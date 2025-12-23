package com.bank.Accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.Getter;

@Data
@Schema(
        name = "Account",
        description = "Schema to hold Account information"
)
public class AccountsDto {


    @NotEmpty
    private Long accountNumber;

    @NotEmpty(message =  "cannot be null or empty")
    private String accountType;

    @NotEmpty(message = "Cannot be null or empty")
    private String branchAddress;
}
