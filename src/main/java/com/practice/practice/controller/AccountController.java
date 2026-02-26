package com.practice.practice.controller;

import com.practice.practice.entity.Account;
import com.practice.practice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService service;

    @PostMapping("/transfer/tx")
    public Account transferTx(@RequestParam double amount) {
        return service.transferWithTx(amount);
    }
}
