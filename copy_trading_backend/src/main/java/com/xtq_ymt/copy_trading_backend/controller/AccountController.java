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
@RequestMapping("/accounts")  // 设定该控制器的根路径为 "/accounts"
@Tag(name = "Account Management", description = "用于管理用户账户的API")  // Swagger文档标签，标记该控制器为账户管理部分
public class AccountController {

    private final AccountService accountService;

    // 使用构造器注入 AccountService
    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    /**
     * 创建账户接口
     * @param userId 用户ID
     * @param initialBalance 初始余额
     * @return 返回创建的账户对象
     */
    @PostMapping("/create")  // 设置该方法处理POST请求，并映射到 "/create" 路径
    @Operation(summary = "创建账户", description = "为用户创建一个新账户。")  // Swagger注释，描述此接口的功能
    public ResponseEntity<Account> createAccount(@RequestParam Long userId, @RequestParam double initialBalance) {
        // 调用AccountService的createAccount方法，创建新账户
        Account account = accountService.createAccount(userId, initialBalance);
        // 返回一个状态码为201（CREATED）的响应，包含新创建的账户对象
        return new ResponseEntity<>(account, HttpStatus.CREATED);
    }

    /**
     * 获取账户详情接口
     * @param userId 用户ID
     * @return 返回用户对应的账户详情
     */
    @GetMapping("/{userId}")  // 设置该方法处理GET请求，并映射到 "/{userId}" 路径
    @Operation(summary = "获取账户详情", description = "获取特定用户的账户详情。")  // Swagger注释，描述此接口的功能
    public ResponseEntity<Account> getAccount(@PathVariable Long userId) {
        // 调用AccountService的getAccountByUserId方法，获取用户的账户信息
        Account account = accountService.getAccountByUserId(userId);
        // 返回状态码200（OK）的响应，包含用户的账户信息
        return new ResponseEntity<>(account, HttpStatus.OK);
    }

    /**
     * 更新账户信息接口
     * @param account 账户对象，包含更新的字段
     * @return 返回更新后的账户信息
     */
    @PutMapping("/update")  // 设置该方法处理PUT请求，并映射到 "/update" 路径
    @Operation(summary = "更新账户", description = "更新一个已存在的账户。")  // Swagger注释，描述此接口的功能
    public ResponseEntity<Account> updateAccount(@RequestBody Account account) {
        // 调用AccountService的updateAccount方法，更新账户信息
        Account updatedAccount = accountService.updateAccount(account);
        // 返回状态码200（OK）的响应，包含更新后的账户信息
        return new ResponseEntity<>(updatedAccount, HttpStatus.OK);
    }
}
