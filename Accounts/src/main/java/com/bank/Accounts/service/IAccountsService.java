package com.bank.Accounts.service;

import com.bank.Accounts.dto.CustomerDto;

public interface IAccountsService {

    void createAccount(CustomerDto customerDto);
}
