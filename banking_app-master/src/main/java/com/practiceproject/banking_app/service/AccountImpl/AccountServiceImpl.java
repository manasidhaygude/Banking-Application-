package com.practiceproject.banking_app.service.AccountImpl;

import com.practiceproject.banking_app.dto.AccountDto;
import com.practiceproject.banking_app.entity.Account;
import com.practiceproject.banking_app.mapper.AccountMapper;
import com.practiceproject.banking_app.repository.AccountRepository;
import com.practiceproject.banking_app.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountServiceImpl(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public AccountDto createaccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedacc = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedacc);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account doesn't exists!"));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account doesn't exists!"));
        double total = account.getBalance() + amount ;
        account.setBalance(total);
        Account savedacc = accountRepository.save(account);
        return AccountMapper.mapToAccountDto(savedacc);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account doesn't exists!"));
        if(account.getBalance() < amount){
            throw new RuntimeException("Insufficient amount!");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedacc = accountRepository.save(account);

        return AccountMapper.mapToAccountDto(savedacc);
    }

    @Override
    public List<AccountDto> getAllAcounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream().map((account) -> AccountMapper.mapToAccountDto(account)).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepository.findById(id).orElseThrow(()->new RuntimeException("Account doesn't exists!"));
        accountRepository.deleteById(id);
    }
}
