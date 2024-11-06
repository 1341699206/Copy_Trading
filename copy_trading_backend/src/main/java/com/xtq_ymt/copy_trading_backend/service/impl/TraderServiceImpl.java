package com.xtq_ymt.copy_trading_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xtq_ymt.copy_trading_backend.dto.TraderDashboardDTO;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.model.TradingAccount;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import com.xtq_ymt.copy_trading_backend.service.TraderService;
import java.math.BigDecimal;
import java.util.NoSuchElementException;

@Service
public class TraderServiceImpl implements TraderService {
    @Autowired
    private TraderRepository traderRepository;

    @Override
    public TraderDashboardDTO getTraderDashboardInfo(Long traderId, Long accountId) {
        // 获取 Trader 信息
        Trader trader = traderRepository.findById(traderId)
            .orElseThrow(() -> new NoSuchElementException("Trader not found with id: " + traderId));

        BigDecimal amountFollowing = trader.getAmountFollowing() != null ? trader.getAmountFollowing() : BigDecimal.ZERO;
        Integer investors = trader.getFollowersWhoFollowed() != null ? trader.getFollowersWhoFollowed().size() : 0;

        // 使用 accountId 查找指定账户信息
        TradingAccount selectedAccount = trader.getTradingAccounts() != null ? trader.getTradingAccounts()
            .stream()
            .filter(account -> account.getAccountId().equals(accountId))
            .findFirst()
            .orElseThrow(() -> new NoSuchElementException("Account not found with id: " + accountId)) : null;

        if (selectedAccount == null) {
            throw new NoSuchElementException("Account not found with id: " + accountId);
        }

        // 获取选定账户的相关指标
        BigDecimal equity = selectedAccount.getEquity() != null ? selectedAccount.getEquity() : BigDecimal.ZERO;
        BigDecimal balance = selectedAccount.getBalance() != null ? selectedAccount.getBalance() : BigDecimal.ZERO;
        BigDecimal realisedPNL = selectedAccount.getRealisedPNL() != null ? selectedAccount.getRealisedPNL() : BigDecimal.ZERO;
        BigDecimal margin = selectedAccount.getMargin() != null ? selectedAccount.getMargin() : BigDecimal.ZERO;
        BigDecimal freeMargin = selectedAccount.getFreeMargin() != null ? selectedAccount.getFreeMargin() : BigDecimal.ZERO;

        // 返回 TraderDashboardDTO
        return new TraderDashboardDTO(amountFollowing, investors, equity, balance, realisedPNL, margin, freeMargin);
    }
}
