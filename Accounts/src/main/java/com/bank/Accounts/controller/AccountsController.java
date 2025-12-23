package com.bank.Accounts.controller;


import com.bank.Accounts.constants.AccountsConstants;
import com.bank.Accounts.dto.CustomerDto;
import com.bank.Accounts.dto.ErrorResponseDto;
import com.bank.Accounts.dto.ResponseDto;
import com.bank.Accounts.entity.Customer;
import com.bank.Accounts.service.IAccountsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "CRUD Rest api for Accounts",
        description = "Create read Update Delete "
)   //Swagger
@RestController
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountsController {

    @Autowired
    private IAccountsService iAccountsService;

    @Operation(summary = "Create account REST Api"
            , description = "Creation of new Customer and Account in the bank")
    @ApiResponse(responseCode = "201", description = "Http status Created")
    @PostMapping("/create")
    public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) {

        iAccountsService.createAccount(customerDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountsConstants.STATUS_201
                        , AccountsConstants.MESSAGE_201));


    }

    @Operation(summary = "Fetch account REST Api"
            , description = "Fetching account from the database")
    @ApiResponse(responseCode = "200", description = "Account fetched")
    @GetMapping("/fetch")
    public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam
                                                           String mobileNumber) {

        CustomerDto customerDto = iAccountsService.fetchAccount(mobileNumber);
        return ResponseEntity.status(HttpStatus.OK)
                .body(customerDto);


    }


    @Operation(summary = "Update of the  account REST Api"
            , description = "Fetching account from the database")
    @ApiResponses(
            {
                    @ApiResponse(responseCode = "200"
                            , description = "Account updated Successfully(OK)"),
                    @ApiResponse(responseCode = "500"
                            , description = "Something wrong occured"
                            , content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    ))
            }
    )
    @PutMapping("/update")
    public ResponseEntity<ResponseDto> update(@Valid @RequestBody CustomerDto customerDto) {
        boolean isUpdated = iAccountsService.updateAccount(customerDto);


        if (isUpdated) {

            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));

        }
    }

    @Operation(summary = "Delete account REST Api"
            , description = "Delete Customer and Account in the bank")
    @ApiResponse(responseCode = "201", description = "Http status Deleted")
    @DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> delete(@RequestParam
                                              @Pattern(regexp = "($|[0-9]10})"
                                                      , message = "Mobile number must be 10 digits")
                                              String mobileNumber) {

        if (iAccountsService.deleteAccount(mobileNumber)) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body(new ResponseDto(AccountsConstants.STATUS_200, AccountsConstants.MESSAGE_200));
        } else {

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDto(AccountsConstants.STATUS_500, AccountsConstants.MESSAGE_500));
        }
    }


}
