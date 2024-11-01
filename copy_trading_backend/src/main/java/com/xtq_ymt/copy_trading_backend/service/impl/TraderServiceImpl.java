package com.xtq_ymt.copy_trading_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xtq_ymt.copy_trading_backend.dto.TraderDashboardDTO;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.model.TradingAccount;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import com.xtq_ymt.copy_trading_backend.service.TraderService;
import java.math.BigDecimal;

@Service
public class TraderServiceImpl implements TraderService {
    @Autowired
    private TraderRepository traderRepository;

    @Override
    public TraderDashboardDTO getTraderDashboardInfo(Long traderId, Long accountId) {
        // 获取 Trader 信息
        Trader trader = traderRepository.findById(traderId)
            .orElseThrow(() -> new RuntimeException("Trader not found with id: " + traderId));

        BigDecimal amountFollowing = trader.getAmountFollowing();
        Integer investors = trader.getFollowersWhoFollowed().size();

        // 使用 accountId 查找指定账户信息
        TradingAccount selectedAccount = trader.getTradingAccounts()
            .stream()
            .filter(account -> account.getAccountId().equals(accountId))
            .findFirst()
            .orElseThrow(() -> new RuntimeException("Account not found with id: " + accountId));

        // 获取选定账户的相关指标
        BigDecimal equity = selectedAccount.getEquity();
        BigDecimal balance = selectedAccount.getBalance();
        BigDecimal realisedPNL = selectedAccount.getRealisedPNL();
        BigDecimal margin = selectedAccount.getMargin();
        BigDecimal freeMargin = selectedAccount.getFreeMargin();

        // 返回 TraderDashboardDTO
        return new TraderDashboardDTO(amountFollowing, investors, equity, balance, realisedPNL, margin, freeMargin);
    }
}
