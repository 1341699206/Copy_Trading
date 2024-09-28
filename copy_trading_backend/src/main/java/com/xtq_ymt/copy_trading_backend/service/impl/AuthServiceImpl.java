package com.xtq_ymt.copy_trading_backend.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.model.Admin;
import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.repository.AdminRepository;
import com.xtq_ymt.copy_trading_backend.repository.FollowerRepository;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import com.xtq_ymt.copy_trading_backend.service.AuthService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j // 替代Logger logger = Logger.getLogger(AuthService.class.getName()); 自动生成日志类 log 的注释
public class AuthServiceImpl implements AuthService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AdminRepository adminRepository;
    
    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Override
    public Result authenticate(String email, String password, String role) {
        log.info("Authenticating user with email: " + email + " and role: " + role);
        switch (role.toUpperCase()) {
            case "ADMIN":
                return authenticateAdmin(email, password);
            case "FOLLOWER":
                return authenticateFollower(email, password);
            case "TRADER":
                return authenticateTrader(email, password);
            default:
                return Result.error("Invalid role specified.");
        }
    }

    private Result authenticateAdmin(String email, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            if (passwordEncoder.matches(password, admin.get().getPassword())) {
                return Result.success("Login successful");
            } else {
                return Result.error("Password is incorrect");
            }
        } else {
            return Result.error("Admin not found");
        }
    }

    private Result authenticateFollower(String email, String password) {
        Optional<Follower> follower = followerRepository.findByEmail(email);
        if (follower.isPresent()) {
            if (passwordEncoder.matches(password, follower.get().getPassword())) {
                return Result.success("Login successful");
            } else {
                return Result.error("Password is incorrect");
            }
        } else {
            return Result.error("Follower not found");
        }
    }

    private Result authenticateTrader(String email, String password) {
        Optional<Trader> trader = traderRepository.findByEmail(email);
        if (trader.isPresent()) {
            if (passwordEncoder.matches(password, trader.get().getPassword())) {
                return Result.success("Login successful");
            } else {
                return Result.error("Password is incorrect");
            }
        } else {
            return Result.error("Trader not found");
        }
    }

    @Override
    public Result register(String name, String email, String password, String role, String country) {
        log.info("Registering user with email: " + email + " and role: " + role);

        // 检查是否已经有用户使用了该邮箱
        if (adminRepository.findByEmail(email).isPresent() ||
            followerRepository.findByEmail(email).isPresent() ||
            traderRepository.findByEmail(email).isPresent()) {
            log.warn("User with email: " + email + " already exists.");
            return Result.error("User with this email already exists.");
        }

        // 对密码进行加密
        String encodedPassword = passwordEncoder.encode(password);
        log.info("Encoded password for user with email: " + email);

        // 根据角色创建对应的用户
        switch (role.toUpperCase()) {
            case "FOLLOWER":
                return registerFollower(name, email, encodedPassword, country);
            case "TRADER":
                return registerTrader(name, email, encodedPassword, country);
            default:
                return Result.error("Invalid role specified.");
        }
    }

    private Result registerFollower(String name, String email, String password, String country) {
        Follower follower = new Follower();
        follower.setName(name);
        follower.setEmail(email);
        follower.setPassword(password);
        follower.setCountry(country);
        follower.setRegistrationDate(new Date());

        // 保存 Follower 到数据库
        followerRepository.save(follower);
        log.info("Follower registered successfully with email: " + email);
        return Result.success("Follower registered successfully");
    }

    private Result registerTrader(String name, String email, String password, String country) {
        Trader trader = new Trader();
        trader.setName(name);
        trader.setEmail(email);
        trader.setPassword(password);
        trader.setCountryIsoCode(country);
        trader.setLastUpdatedDate(new Date());

        // 保存 Trader 到数据库
        traderRepository.save(trader);
        log.info("Trader registered successfully with email: " + email);
        return Result.success("Trader registered successfully");
    }
}
