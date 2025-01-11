package com.practiceproject.banking_app.service;

import com.practiceproject.banking_app.dto.AccountDto;

import java.util.List;

public interface AccountService {
    AccountDto createaccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id,double amount);

    AccountDto withdraw(Long id,double amount);

    List<AccountDto> getAllAcounts();

    void deleteAccount(Long id);
}
