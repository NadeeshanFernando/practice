package com.practice.practice.service;

import com.practice.practice.entity.Account;
import com.practice.practice.repository.AccountRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountService {

    private final AccountRepo accountRepo;

    @Transactional
    public Account transferWithTx(double amount){

        Account from = accountRepo.findById(1234L).orElseThrow();
        Account to = accountRepo.findById(5678L).orElseThrow();

        from.setBalance(from.getBalance() - amount);
        accountRepo.save(from);
        log.info("Money Deducted From: {} and balance {}",from.getAccNum(),from.getBalance());

        if (amount > from.getBalance()) {
            throw new RuntimeException("Insufficient balance - rollback money");
        }

        to.setBalance(to.getBalance() + amount);
        accountRepo.save(to);
        log.info("Money Transferred To: {} and balance {}",to.getAccNum(),to.getBalance());

        return accountRepo.findById(1234L).orElseThrow();
    }
}