package com.bank.Accounts.service;

import com.bank.Accounts.dto.CustomerDetailsDto;

public interface ICustomerService {


    CustomerDetailsDto fetchCustomerDetails(String mobileNumber);

}
