package com.bank.Accounts.controller;


import com.bank.Accounts.dto.CustomerDetailsDto;
import com.bank.Accounts.service.ICustomerService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@Tag(
        name = "Rest api for Customer in BanklyCore",
        description = "Create read Update Delete "
)   //Swagger
@RequestMapping(path = "/api", produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
@RestController
@AllArgsConstructor

public class CustomerController {

    private final ICustomerService customerService;

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);


    @GetMapping("/fetchCustomerDetails")
    public ResponseEntity<CustomerDetailsDto> fetchCustomerDetails(@RequestHeader("banklycore-correlation-id") String correlationId
            , @RequestParam String mobileNumber) {


        logger.debug("banklycore-correlation-id {}", correlationId);
        CustomerDetailsDto customerDetails = customerService.fetchCustomerDetails(mobileNumber, correlationId);
        return ResponseEntity.status(HttpStatus.OK).body(customerDetails);

    }

}
