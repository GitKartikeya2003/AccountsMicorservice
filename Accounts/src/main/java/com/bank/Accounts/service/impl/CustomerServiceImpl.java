package com.bank.Accounts.service.impl;


import com.bank.Accounts.clients.CardsFeignClients;
import com.bank.Accounts.clients.LoansFeignClients;
import com.bank.Accounts.dto.AccountsDto;
import com.bank.Accounts.dto.CardsDto;
import com.bank.Accounts.dto.CustomerDetailsDto;
import com.bank.Accounts.dto.LoansDto;
import com.bank.Accounts.entity.Accounts;
import com.bank.Accounts.entity.Customer;
import com.bank.Accounts.exception.ResourceNotFoundException;
import com.bank.Accounts.mapper.AccountsMapper;
import com.bank.Accounts.mapper.CustomerMapper;
import com.bank.Accounts.repository.AccountsRepository;
import com.bank.Accounts.repository.CustomerRepository;
import com.bank.Accounts.service.ICustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerServiceImpl implements ICustomerService {

    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;
    private CardsFeignClients cardsFeignClients;
    private LoansFeignClients loansFeignClients;


    @Override
    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber, String correlationId) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDetailsDto customerDetails = CustomerMapper.mapToCustomerDetailsDto(customer, new CustomerDetailsDto());
        customerDetails.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));


        ResponseEntity<LoansDto> loansDtoResponseEntity = loansFeignClients.fetchLoanDetails(correlationId, mobileNumber);
        if (null != loansDtoResponseEntity) {
            customerDetails.setLoansDto(loansDtoResponseEntity.getBody());
        }

        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClients.fetchCardDetails(correlationId, mobileNumber);
        if (null != cardsDtoResponseEntity) {
            customerDetails.setCardsDto(cardsDtoResponseEntity.getBody());

        }

        return customerDetails;
    }
}
