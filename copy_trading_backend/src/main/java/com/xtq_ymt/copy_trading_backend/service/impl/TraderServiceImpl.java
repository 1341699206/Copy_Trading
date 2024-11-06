package com.xtq_ymt.copy_trading_backend.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xtq_ymt.copy_trading_backend.dto.TraderDashboardDTO;
import com.xtq_ymt.copy_trading_backend.dto.CopiersDTO;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.model.TradingAccount;  // 确保正确导入 TradingAccount
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import com.xtq_ymt.copy_trading_backend.service.TraderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.math.BigDecimal;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

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

    @Override
    public Page<CopiersDTO> getTraderCopiers(Long traderId, int currentPage, int pageSize) {
        // 创建分页请求
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize, Sort.by(Sort.Direction.DESC, "profitLoss"));

        // 获取指定的 Trader
        Trader trader = traderRepository.findById(traderId)
                .orElseThrow(() -> new NoSuchElementException("Trader not found with id: " + traderId));

        // 获取跟随该 Trader 的所有 followers，并按收益降序排序
        List<Follower> followers = trader.getFollowersWhoFollowed().stream()
                .sorted((f1, f2) -> f2.getProfitLoss().compareTo(f1.getProfitLoss()))
                .collect(Collectors.toList());

        // 将 Follower 转换为 CopiersDTO
        List<CopiersDTO> copiersDTOList = followers.stream().map(follower -> {
            CopiersDTO dto = new CopiersDTO();
            dto.setFollowerId(follower.getFollowerId());
            dto.setName(follower.getName());
            dto.setProfit(follower.getProfitLoss());
            return dto;
        }).collect(Collectors.toList());

        // 计算分页内容
        int start = (int) pageRequest.getOffset();
        int end = Math.min((start + pageRequest.getPageSize()), copiersDTOList.size());
        return new PageImpl<>(copiersDTOList.subList(start, end), pageRequest, copiersDTOList.size());
    }
}
