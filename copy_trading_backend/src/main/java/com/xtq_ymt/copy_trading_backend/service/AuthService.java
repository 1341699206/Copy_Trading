package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Admin;
import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.repository.AdminRepository;
import com.xtq_ymt.copy_trading_backend.repository.FollowerRepository;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthService {

    private static final Logger logger = Logger.getLogger(AuthService.class.getName());

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // 新的响应对象，用于返回详细的认证结果
    public static class AuthResult {
        private boolean success;
        private String message;

        public AuthResult(boolean success, String message) {
            this.success = success;
            this.message = message;
        }

        public boolean isSuccess() {
            return success;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * 处理用户的认证请求，返回更详细的结果
     */
    public AuthResult authenticate(String email, String password, String role) {
        logger.info("Authenticating user with email: " + email + " and role: " + role);
        switch (role.toUpperCase()) {
            case "ADMIN":
                return authenticateAdmin(email, password);
            case "FOLLOWER":
                return authenticateFollower(email, password);
            case "TRADER":
                return authenticateTrader(email, password);
            default:
                return new AuthResult(false, "Invalid role specified.");
        }
    }

    private AuthResult authenticateAdmin(String email, String password) {
        Optional<Admin> admin = adminRepository.findByEmail(email);
        if (admin.isPresent()) {
            if (passwordEncoder.matches(password, admin.get().getPassword())) {
                return new AuthResult(true, "Login successful");
            } else {
                return new AuthResult(false, "Password is incorrect");
            }
        } else {
            return new AuthResult(false, "Admin not found");
        }
    }

    private AuthResult authenticateFollower(String email, String password) {
        Optional<Follower> follower = followerRepository.findByEmail(email);
        if (follower.isPresent()) {
            if (passwordEncoder.matches(password, follower.get().getPassword())) {
                return new AuthResult(true, "Login successful");
            } else {
                return new AuthResult(false, "Password is incorrect");
            }
        } else {
            return new AuthResult(false, "Follower not found");
        }
    }

    private AuthResult authenticateTrader(String email, String password) {
        Optional<Trader> trader = traderRepository.findByEmail(email);
        if (trader.isPresent()) {
            if (passwordEncoder.matches(password, trader.get().getPassword())) {
                return new AuthResult(true, "Login successful");
            } else {
                return new AuthResult(false, "Password is incorrect");
            }
        } else {
            return new AuthResult(false, "Trader not found");
        }
    }

    /**
     * 处理用户注册请求
     */
    public boolean register(String name, String email, String password, String role, String country) {
        logger.info("Registering user with email: " + email + " and role: " + role);

        // 检查是否已经有用户使用了该邮箱
        if (adminRepository.findByEmail(email).isPresent() ||
            followerRepository.findByEmail(email).isPresent() ||
            traderRepository.findByEmail(email).isPresent()) {
            logger.warning("User with email: " + email + " already exists.");
            throw new IllegalArgumentException("User with this email already exists.");
        }

        // 对密码进行加密
        String encodedPassword = passwordEncoder.encode(password);
        logger.info("Encoded password for user with email: " + email);

        // 根据角色创建对应的用户
        switch (role.toUpperCase()) {
            case "FOLLOWER":
                return registerFollower(name, email, encodedPassword, country);
            case "TRADER":
                return registerTrader(name, email, encodedPassword, country);
            default:
                throw new IllegalArgumentException("Invalid role specified.");
        }
    }

    private boolean registerFollower(String name, String email, String password, String country) {
        Follower follower = new Follower();
        follower.setName(name);
        follower.setEmail(email);
        follower.setPassword(password);
        follower.setCountry(country);
        follower.setRegistrationDate(new Date());

        // 保存 Follower 到数据库
        followerRepository.save(follower);
        logger.info("Follower registered successfully with email: " + email);
        return true;
    }

    private boolean registerTrader(String name, String email, String password, String country) {
        Trader trader = new Trader();
        trader.setName(name);
        trader.setEmail(email);
        trader.setPassword(password);
        trader.setCountryIsoCode(country);
        trader.setLastUpdatedDate(new Date());

        // 保存 Trader 到数据库
        traderRepository.save(trader);
        logger.info("Trader registered successfully with email: " + email);
        return true;
    }
}
