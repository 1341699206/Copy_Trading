package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.MarketDataDTO;
import com.xtq_ymt.copy_trading_backend.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/market-data")
public class MarketDataController {

    @Autowired
    private MarketDataService marketDataService;

    // 获取单个 symbol 对应的市场数据
    @GetMapping
    public Result getMarketData(@RequestParam String symbol) {
        MarketDataDTO data = marketDataService.getMarketDataBySymbol(symbol);
        if (data != null) {
            return Result.success("Data fetched successfully", data);
        } else {
            return Result.error("No data found for symbol: " + symbol);
        }
    }

    // 获取所有可用的市场数据
    @GetMapping("/available")
    public Result getAllAvailableMarketData() {
        List<MarketDataDTO> dataList = marketDataService.getAllAvailableMarketData();
        if (!dataList.isEmpty()) {
            return Result.success("Available market data fetched successfully", dataList);
        } else {
            return Result.error("No available market data found");
        }
    }
}