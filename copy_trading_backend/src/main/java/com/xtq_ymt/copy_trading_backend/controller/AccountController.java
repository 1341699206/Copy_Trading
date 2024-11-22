package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.model.Account;
import com.xtq_ymt.copy_trading_backend.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/accounts")
@Tag(name = "Account Management", description = "APIs for managing user accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create account", description = "Creates a new account for a user.")
    public ResponseEntity<Account> createAccount(@RequestParam Long userId, @RequestParam double initialBalance) {
        Account account = accountService.createAccount(userId, initialBalance);
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @Operation(summary = "Get account details", description = "Fetches the account details for a specific user.")
    public ResponseEntity<Account> getAccount(@PathVariable Long userId) {
        Account account = accountService.getAccountByUserId(userId);
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @PutMapping("/update")
    @Operation(summary = "Update account", description = "Updates an existing account.")
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        Account updatedAccount = accountService.updateAccount(account);
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
}
