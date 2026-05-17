package com.bank.Accounts.clients;


import com.bank.Accounts.dto.LoansDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "loans",fallback = LoansFallback.class)
public interface LoansFeignClients {

    @GetMapping("/api/fetch")
    public ResponseEntity<LoansDto> fetchLoanDetails(@RequestHeader("banklycore-correlation-id") String correlationId,
                                                     @RequestParam String mobileNumber);

}
