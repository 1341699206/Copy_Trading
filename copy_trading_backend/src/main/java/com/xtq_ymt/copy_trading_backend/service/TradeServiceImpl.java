package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.repository.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public Trade openTrade(Long accountId, Long strategyId, String symbol, String type, double lotSize, double priceOpen) {
        Trade trade = new Trade();
        trade.setAccountId(accountId);
        trade.setStrategyId(strategyId);
        trade.setSymbol(symbol);
        trade.setType(type);
        trade.setLotSize(lotSize);
        trade.setPriceOpen(priceOpen);
        trade.setClosed(false);
        return tradeRepository.save(trade);
    }

    @Override
    public Trade closeTrade(Long tradeId, double priceClose) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new IllegalArgumentException("Trade not found"));
        if (trade.isClosed()) {
            throw new IllegalStateException("Trade is already closed");
        }
        trade.setPriceClose(priceClose);
        trade.setProfit((priceClose - trade.getPriceOpen()) * trade.getLotSize());
        trade.setClosed(true);
        trade.setDateClose(LocalDateTime.now());
        return tradeRepository.save(trade);
    }

    @Override
    public List<Trade> getOpenTradesByAccountId(Long accountId) {
        return tradeRepository.findByAccountIdAndIsClosedFalse(accountId);
    }

    @Override
    public List<Trade> getTradesByStrategyId(Long strategyId) {
        return tradeRepository.findByStrategyId(strategyId);
    }
}
