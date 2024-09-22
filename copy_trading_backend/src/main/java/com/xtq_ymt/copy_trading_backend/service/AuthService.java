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

@Service
public class AuthService {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private FollowerRepository followerRepository;

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public boolean authenticate(String email, String password, String role) {
        switch (role.toUpperCase()) {
            case "ADMIN":
                return authenticateAdmin(email, password);
            case "FOLLOWER":
                return authenticateFollower(email, password);
            case "TRADER":
                return authenticateTrader(email, password);
            default:
                throw new IllegalArgumentException("Invalid role");
        }
    }

    private boolean authenticateAdmin(String email, String password) {
        Admin admin = adminRepository.findByEmail(email).orElse(null);
        return admin != null && passwordEncoder.matches(password, admin.getPassword());
    }

    private boolean authenticateFollower(String email, String password) {
        Follower follower = followerRepository.findByEmail(email).orElse(null);
        return follower != null && passwordEncoder.matches(password, follower.getPassword());
    }

    private boolean authenticateTrader(String email, String password) {
        Trader trader = traderRepository.findByEmail(email).orElse(null);
        return trader != null && passwordEncoder.matches(password, trader.getPassword());
    }
}
