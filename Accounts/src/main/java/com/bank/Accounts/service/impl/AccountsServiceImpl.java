package com.bank.Accounts.service.impl;


import com.bank.Accounts.constants.AccountsConstants;
import com.bank.Accounts.dto.AccountsDto;
import com.bank.Accounts.dto.CustomerDto;
import com.bank.Accounts.entity.Accounts;
import com.bank.Accounts.entity.Customer;
import com.bank.Accounts.exception.CustomerAlreadyExistException;
import com.bank.Accounts.exception.ResourceNotFoundException;
import com.bank.Accounts.mapper.AccountsMapper;
import com.bank.Accounts.mapper.CustomerMapper;
import com.bank.Accounts.repository.AccountsRepository;
import com.bank.Accounts.repository.CustomerRepository;
import com.bank.Accounts.service.IAccountsService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public class AccountsServiceImpl implements IAccountsService {


    private AccountsRepository accountsRepository;
    private CustomerRepository customerRepository;

    @Override
    public void createAccount(CustomerDto customerDto) {
        Customer customer = CustomerMapper.mapToCustomer(customerDto, new Customer());
        Optional<Customer> optionalCustomer = customerRepository.findByMobileNumber(customerDto.getMobileNumber());

        if (optionalCustomer.isPresent()) {
            throw new CustomerAlreadyExistException("Customer Already Register with this Phone Number" + customerDto.getMobileNumber());
        }
        Customer savedCustomer = customerRepository.save(customer);
//        savedCustomer.setCreatedAt(LocalDateTime.now());
//        savedCustomer.setCreatedBy("Anonymous");
        accountsRepository.save(createNewAccount(savedCustomer));
    }


    private Accounts createNewAccount(Customer customer) {

        Accounts newAccount = new Accounts();
        newAccount.setCustomerId(customer.getCustomerId());
        long randomAccNumber = 1000000000L + new Random().nextInt(900000000);

        newAccount.setAccountNumber(randomAccNumber);

//        newAccount.setCreatedAt(LocalDateTime.now());
//        newAccount.setCreatedBy("Anonymous");
        newAccount.setAccountType(AccountsConstants.SAVINGS);
        newAccount.setBranchAddress(AccountsConstants.ADDRESS);
        return newAccount;
    }


    @Override
    public CustomerDto fetchAccount(String mobileNumber) {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Accounts accounts = accountsRepository.findByCustomerId(customer.getCustomerId()).orElseThrow(
                () -> new ResourceNotFoundException("Account", "customerId", customer.getCustomerId().toString())
        );
        CustomerDto customerDto = CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
        customerDto.setAccountsDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
        return customerDto;
    }

    @Override
    public boolean updateAccount(CustomerDto customerDto) {
        boolean isUpdate = false;

        AccountsDto accountsDto = customerDto.getAccountsDto();

        if (accountsDto != null) {

            Accounts accounts = accountsRepository.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFoundException("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );

            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountsRepository.save(accounts);


            Long customerId = accounts.getCustomerId();

            Customer customer = customerRepository.findById(customerId).orElseThrow(
                    () -> new ResourceNotFoundException("Customer", "CustomerId", customerId.toString())
            );

            CustomerMapper.mapToCustomer(customerDto, customer);
            customer = customerRepository.save(customer);

            isUpdate = true;
        }
        return isUpdate;
    }

    @Override
    public boolean deleteAccount(String mobileNumber) {

        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(
                () -> new ResourceNotFoundException("Customer", "mobileNumber", mobileNumber)
        );
        Long customerId = customer.getCustomerId();

//        Accounts accounts = accountsRepository.findByCustomerId(customerId).orElseThrow(
//                () -> new ResourceNotFoundException("Account", "customerId", customerId.toString())
//
//        ); //another way

        accountsRepository.deleteByCustomerId(customerId);
        customerRepository.deleteById(customerId);

        return true;
    }


}
