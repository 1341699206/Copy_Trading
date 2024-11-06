package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.dto.TradeHistoryDTO;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.repository.TradeRepository;
import com.xtq_ymt.copy_trading_backend.service.TradeHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class TradeHistoryServiceImpl implements TradeHistoryService {

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeHistoryServiceImpl(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }

    @Override
    public Page<TradeHistoryDTO> getTradeHistory(Long traderId, int pageSize, int currentPage) {
        PageRequest pageRequest = PageRequest.of(currentPage - 1, pageSize);
        
        // 查询已平仓的交易记录
        Page<Trade> page = tradeRepository.findByTrader_TraderIdAndIsOpenFalse(traderId, pageRequest);

        return page.map(trade -> {
            TradeHistoryDTO dto = new TradeHistoryDTO();
            dto.setTradeId(trade.getTradeId());
            dto.setCurrency(trade.getCurrency());
            dto.setDateOpen(trade.getDateOpen());
            dto.setType(trade.getType().toString());
            dto.setDateClose(trade.getDateClose());
            dto.setStandardLots(trade.getStandardLots());
            dto.setPriceOpen(trade.getPriceOpen());
            dto.setPriceClose(trade.getPriceClose());
            dto.setHighestProfit(trade.getHighestProfitPips());
            dto.setWorstDrawdown(trade.getWorstDrawdownPips());
            dto.setInterest(trade.getInterestUsd());
            dto.setProfit(trade.getProfitUsd());
            return dto;
        });
    }
}
