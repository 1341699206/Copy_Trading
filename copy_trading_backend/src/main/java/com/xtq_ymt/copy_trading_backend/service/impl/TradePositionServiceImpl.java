package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.dto.TradeOpenPositionDTO;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.repository.MarketDataRepository;
import com.xtq_ymt.copy_trading_backend.repository.TradeRepository;
import com.xtq_ymt.copy_trading_backend.service.TradePositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class TradePositionServiceImpl implements TradePositionService {

    private final TradeRepository tradeRepository;
    private final MarketDataRepository marketDataRepository;

    @Autowired
    public TradePositionServiceImpl(TradeRepository tradeRepository, MarketDataRepository marketDataRepository) {
        this.tradeRepository = tradeRepository;
        this.marketDataRepository = marketDataRepository;
    }

    @Override
    public Page<TradeOpenPositionDTO> getOpenPositions(Long traderId, int pageSize, int currentPage) {
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);
        Page<Trade> openTrades = tradeRepository.findByTrader_TraderIdAndIsOpenTrue(traderId, pageRequest);

        return openTrades.map(trade -> {
            TradeOpenPositionDTO dto = new TradeOpenPositionDTO();
            dto.setTrade(trade.getTradeId());  // 注意这里的字段名改为 `trade`
            dto.setCurrency(trade.getCurrency());
            dto.setDateOpen(trade.getDateOpen());
            dto.setType(trade.getType().toString());
            dto.setStandardLots(trade.getStandardLots());
            dto.setPriceOpen(trade.getPriceOpen());

            // 使用 MarketDataRepository 获取当前价格
            BigDecimal currentPrice = marketDataRepository.findLatestByInstrument(trade.getCurrency()).getCurrentPrice();
            dto.setPriceCurrent(currentPrice);

            dto.setProfit(trade.getProfitUsd() != null ? trade.getProfitUsd() : BigDecimal.ZERO);
            dto.setStop(BigDecimal.valueOf(100));  // 假设生成一些随机值，或者提供默认值
            dto.setLimit(BigDecimal.valueOf(200)); // 假设生成一些随机值，或者提供默认值
            return dto;
        });
    }
}
