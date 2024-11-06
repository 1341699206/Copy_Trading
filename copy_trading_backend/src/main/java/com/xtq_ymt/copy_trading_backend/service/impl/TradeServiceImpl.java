package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.dto.ClosePositionRequest;
import com.xtq_ymt.copy_trading_backend.dto.OpenPositionRequest;
import com.xtq_ymt.copy_trading_backend.model.*;
import com.xtq_ymt.copy_trading_backend.repository.*;
import com.xtq_ymt.copy_trading_backend.service.TradeService;
import com.xtq_ymt.copy_trading_backend.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradeRepository tradeRepository;

    @Autowired
    private TradingAccountRepository tradingAccountRepository;

    @Autowired
    private MarketDataService marketDataService;

    @Override
    @Transactional
    public Trade openPosition(OpenPositionRequest request) {
        // 获取交易账户
        TradingAccount account = tradingAccountRepository.findById(request.getAccountId())
                .orElseThrow(() -> new IllegalArgumentException("交易账户未找到"));

        // 从账户中获取杠杆
        BigDecimal leverage = account.getLeverage();

        // 检查保证金是否足够
        BigDecimal requiredMargin = calculateRequiredMargin(request.getVolume(), leverage);
        if (account.getFreeMargin().compareTo(requiredMargin) < 0) {
            throw new IllegalArgumentException("保证金不足以开仓");
        }

        // 获取当前市场价格
        BigDecimal openPrice = marketDataService.getCurrentPrice(request.getInstrument());

        // 创建交易记录
        Trade trade = new Trade();
        trade.setTraderAccount(account);
        trade.setCurrency(request.getInstrument()); // 使用 Currency 字段
        trade.setPriceOpen(openPrice); // 使用 Price_Open 字段
        trade.setDateOpen(LocalDateTime.now(ZoneId.systemDefault())); // 使用 LocalDateTime 类型的 Date_Open 字段
        trade.setType(request.getActionType()); // 使用 Type 字段
        trade.setStandardLots(request.getVolume()); // 使用 Standard_Lots 字段
        trade.setProfitUsd(BigDecimal.ZERO); // 初始化利润为 0
        trade.setProviderTicket("sample_ticket"); // 示例票据编号，实际可根据需求生成
        trade.setBrokerTicket("sample_broker_ticket"); // 示例经纪商票据编号

        // 保存交易记录
        tradeRepository.save(trade);

        // 更新账户的可用保证金
        account.setFreeMargin(account.getFreeMargin().subtract(requiredMargin));
        tradingAccountRepository.save(account);

        return trade;
    }

    @Override
    @Transactional
    public Trade closePosition(ClosePositionRequest request) {
        // 获取要关闭的交易记录
        Trade trade = tradeRepository.findById(request.getTradeId())
                .orElseThrow(() -> new IllegalArgumentException("交易未找到"));

        // 确认交易是开放状态
        if (trade.getDateClose() != null) {
            throw new IllegalArgumentException("该交易已被关闭");
        }

        // 获取当前市场价格
        BigDecimal closePrice = marketDataService.getCurrentPrice(trade.getCurrency());

        // 更新交易记录
        trade.setPriceClose(closePrice); // 使用 Price_Close 字段
        trade.setDateClose(LocalDateTime.now(ZoneId.systemDefault())); // 使用 LocalDateTime 类型的 Date_Close 字段

        // 计算盈利或亏损
        BigDecimal profitLoss = calculateProfitLoss(trade.getPriceOpen(), closePrice, trade.getStandardLots());
        trade.setProfitUsd(profitLoss); // 使用 Profit_USD 字段
        tradeRepository.save(trade);

        // 更新账户余额
        TradingAccount account = trade.getTraderAccount();
        account.setBalance(account.getBalance().add(profitLoss));
        account.setFreeMargin(account.getFreeMargin().add(trade.getStandardLots())); // 释放保证金
        tradingAccountRepository.save(account);

        return trade;
    }

    private BigDecimal calculateProfitLoss(BigDecimal openPrice, BigDecimal closePrice, BigDecimal volume) {
        return closePrice.subtract(openPrice).multiply(volume);
    }

    private BigDecimal calculateRequiredMargin(BigDecimal volume, BigDecimal leverage) {
        return volume.divide(leverage, 2, RoundingMode.HALF_UP);
    }
}
