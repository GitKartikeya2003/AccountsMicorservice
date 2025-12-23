package com.bank.Accounts.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {


    @NotEmpty(message = "Name cannot be empty")
    private String name;

    @NotEmpty(message = "email show not be null or empty")
    @Email(message = "Email should be a valid value")
    private String email;


    @Pattern(regexp = "($|[0-9]{10})",message = "Must be 10 digits")
    private String mobileNumber;


    private AccountsDto accountsDto;
}
