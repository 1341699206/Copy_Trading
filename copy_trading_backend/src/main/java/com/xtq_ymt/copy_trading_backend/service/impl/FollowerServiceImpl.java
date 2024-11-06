package com.xtq_ymt.copy_trading_backend.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.TraderBasicInfoDTO;
import com.xtq_ymt.copy_trading_backend.dto.TraderDTO;
import com.xtq_ymt.copy_trading_backend.dto.TradersDataDTO;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.model.TradingAccount;
import com.xtq_ymt.copy_trading_backend.repository.TradeRepository;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import com.xtq_ymt.copy_trading_backend.repository.TradingAccountRepository;
import com.xtq_ymt.copy_trading_backend.service.FollowerService;

import lombok.extern.slf4j.Slf4j;
import java.util.Optional;
import java.math.RoundingMode;

@Service
@Slf4j
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    private TraderRepository traderRepository;

    @Autowired
    private TradingAccountRepository tradingAccountRepository;
    
    @Autowired
    private TradeRepository tradeRepository;

    @Override
    public Result getTopTradersData(Integer quantity, Integer timePeriod) {
        Pageable pageable = PageRequest.of(0, quantity);
        List<Trader> traders;

        if (timePeriod != null && timePeriod > 0) {
            LocalDateTime startDate = LocalDateTime.now().minusDays(timePeriod);
            LocalDateTime endDate = LocalDateTime.now();
            traders = traderRepository.findTopByROIDescWithinDateRange(startDate, endDate, pageable);
        } else {
            traders = traderRepository.findTopByOrderByROIDesc(pageable);
        }

        List<TradersDataDTO> topTraders = new ArrayList<>();
        for (Trader trader : traders) {
            TradersDataDTO traderData = new TradersDataDTO(
                    trader.getTraderId(),
                    trader.getName(),
                    trader.getROI(),
                    Optional.ofNullable(trader.getFollowers()).orElse(0),
                    trader.getFollowersWhoFavorited().size(),
                    trader.getPerformance()
            );
            topTraders.add(traderData);
        }
        return Result.success("Getting traders succeeded.", topTraders);
    }

    @Override
    public Result getTraderBasicInfo(Long id) {
        Optional<Trader> traderOptional = traderRepository.findById(id);

        if (traderOptional.isPresent()) {
            Trader trader = traderOptional.get();

            List<TradingAccount> tradingAccounts = tradingAccountRepository.findByTrader_TraderId(trader.getTraderId());
            Double winRate = 0.0;
            if (tradingAccounts != null && !tradingAccounts.isEmpty()) {
                winRate = tradingAccounts.get(0).getWinRate();
            }

            TraderBasicInfoDTO traderBasicInfoDTO = new TraderBasicInfoDTO(
                trader.getTraderId(),
                trader.getName(),
                Optional.ofNullable(trader.getROI()).orElse(BigDecimal.ZERO),
                Optional.ofNullable(trader.getFollowers()).orElse(0),
                winRate
            );
            return Result.success("Trader basic info retrieved successfully.", traderBasicInfoDTO);
        } else {
            return Result.error("Trader not found with ID: " + id);
        }
    }

    public Result getTraderDetailedInfo(Long id, Integer timePeriod) {
        Optional<Trader> traderOptional = traderRepository.findById(id);
        if (traderOptional.isEmpty()) {
            return Result.error("Trader not found with ID: " + id);
        }

        Trader trader = traderOptional.get();
        Pageable pageable = PageRequest.of(0, 20); // 你可以根据需求调整分页大小
        List<Trade> tradeHistory = tradeRepository.findByTrader_TraderId(trader.getTraderId(), pageable).getContent();
        List<TraderDTO.TraderHistory> traderHistoryData = new ArrayList<>();
        
        BigDecimal totalProfit = BigDecimal.ZERO;
        int closedTrades = 0;
        int winTrades = 0;
        BigDecimal totalPips = BigDecimal.ZERO;
        Duration totalTradeTime = Duration.ZERO;

        for (Trade trade : tradeHistory) {
            if (trade.getDateClose() != null) {
                closedTrades++;
                totalProfit = totalProfit.add(trade.getProfitUsd());
                totalPips = totalPips.add(trade.getProfitPips());
                
                Duration tradeDuration = Duration.between(
                    trade.getDateOpen(), 
                    trade.getDateClose()
                );
                totalTradeTime = totalTradeTime.plus(tradeDuration);

                if (trade.getProfitUsd().compareTo(BigDecimal.ZERO) > 0) {
                    winTrades++;
                }
            }

            TraderDTO.TraderHistory history = new TraderDTO.TraderHistory(
                trader.getTraderId(),
                trader.getROI(),
                trade.getProfitUsd(),
                trade.getStandardLots().multiply(trade.getPriceOpen()),
                trade.getProfitPips(),
                trade.getDateOpen()
            );
            traderHistoryData.add(history);
        }

        int avgTradeTime = closedTrades > 0 ? (int) totalTradeTime.toSeconds() / closedTrades : 0;
        BigDecimal avgProfit = closedTrades > 0 ? totalProfit.divide(BigDecimal.valueOf(closedTrades), RoundingMode.HALF_UP) : BigDecimal.ZERO;
        BigDecimal avgPips = closedTrades > 0 ? totalPips.divide(BigDecimal.valueOf(closedTrades), RoundingMode.HALF_UP) : BigDecimal.ZERO;

        TraderDTO traderDTO = new TraderDTO();
        traderDTO.setTraderId(trader.getTraderId());
        traderDTO.setProfit(totalProfit);
        traderDTO.setTrades(closedTrades);
        traderDTO.setMaxOpenTrades(trader.getMaxOpenTrades() != null ? trader.getMaxOpenTrades() : 0); 
        traderDTO.setAvgProfit(avgProfit);
        traderDTO.setWinTrades(winTrades);
        traderDTO.setRecommendedMinInvestment(BigDecimal.ZERO);
        traderDTO.setMaxDrawDown(trader.getMaximumDrawdown());
        traderDTO.setAvgTradeTime(avgTradeTime);
        traderDTO.setAvgPips(avgPips);
        traderDTO.setTraderHistoryData(traderHistoryData);

        return Result.success("Trader detailed info retrieved successfully.", traderDTO);
    }

}
