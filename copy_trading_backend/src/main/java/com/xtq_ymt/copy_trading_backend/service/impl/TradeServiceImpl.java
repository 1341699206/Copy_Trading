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
import java.util.Date;

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

        // 检查保证金是否足够
        BigDecimal requiredMargin = calculateRequiredMargin(request.getVolume(), request.getLeverage());
        if (account.getFreeMargin().compareTo(requiredMargin) < 0) {
            throw new IllegalArgumentException("保证金不足以开仓");
        }

        // 获取当前市场价格
        BigDecimal openPrice = marketDataService.getCurrentPrice(request.getInstrument());

        // 创建交易记录
        Trade trade = new Trade();
        trade.setTraderAccount(account);
        trade.setInstrument(request.getInstrument());
        trade.setOpenPrice(openPrice);
        trade.setOpenTime(new Date());
        trade.setIsOpen(true);
        trade.setVolume(request.getVolume());
        trade.setLeverage(request.getLeverage());
        trade.setTradeNature(TradeNature.ORIGINAL);
        trade.setTradeActionType(request.getActionType());
        trade.setMarginUsed(requiredMargin);

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

        if (!trade.getIsOpen()) {
            throw new IllegalArgumentException("该交易已被关闭");
        }

        // 获取当前市场价格
        BigDecimal closePrice = marketDataService.getCurrentPrice(trade.getInstrument());

        // 更新交易记录
        trade.setClosePrice(closePrice);
        trade.setCloseTime(new Date());
        trade.setIsOpen(false);

        // 计算盈利或亏损
        BigDecimal profitLoss = calculateProfitLoss(trade.getOpenPrice(), closePrice, trade.getVolume());
        trade.setProfitLoss(profitLoss);
        tradeRepository.save(trade);

        // 更新账户余额
        TradingAccount account = trade.getTraderAccount();
        account.setBalance(account.getBalance().add(profitLoss));
        account.setFreeMargin(account.getFreeMargin().add(trade.getMarginUsed()));
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