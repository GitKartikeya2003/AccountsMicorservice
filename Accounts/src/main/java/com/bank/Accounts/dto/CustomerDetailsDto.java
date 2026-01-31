package com.bank.Accounts.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
@Schema(
        name = "CustomerDetails",
        description = "Schema to hold Customer, Accounts, Cards and Loans information"
)
public class CustomerDetailsDto {




    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "email show not be null or empty")
    @Email(message = "Email should be a valid value")
    private String email;


    @Pattern(regexp = "($|[0-9]{10})",message = "Must be 10 digits")
    private String mobileNumber;


    private AccountsDto accountsDto;


    private CardsDto cardsDto;

    private LoansDto loansDto;
}
