package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.MarketDataDTO;
import com.xtq_ymt.copy_trading_backend.service.MarketDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping("/api/market-data")
public class MarketDataController {

    private static final Logger logger = LoggerFactory.getLogger(MarketDataController.class);

    @Autowired
    private MarketDataService marketDataService;

    // 获取指定 symbol 对应的市场数据
    @GetMapping
    public Result getMarketData(@RequestParam String symbol) {
        logger.info("Received request to get market data for symbol: {}", symbol);
        List<MarketDataDTO> dataList = marketDataService.getMarketDataBySymbol(symbol); // 返回包含 openPrice 的数据
        if (dataList != null && !dataList.isEmpty()) {
            logger.info("Successfully fetched market data for symbol: {}", symbol);
            logger.debug("Market data list details with openPrice: {}", dataList); // 包含 openPrice 信息
            return Result.success("Data fetched successfully", dataList);
        } else {
            logger.warn("No data found for symbol: {}", symbol);
            return Result.error("No data found for symbol: " + symbol);
        }
    }

    // 获取所有可用的市场数据
    @GetMapping("/available")
    public Result getAllAvailableMarketData() {
        logger.info("Received request to get all available market data");
        List<MarketDataDTO> dataList = marketDataService.getAllAvailableMarketData(); // 返回包含 openPrice 的数据
        logger.info("After calling marketDataService.getAllAvailableMarketData()");
        logger.debug("Data list with openPrice received by Controller: {}", dataList);
        if (!dataList.isEmpty()) {
            logger.info("Successfully fetched all available market data");
            logger.debug("Market data list details with openPrice: {}", dataList);
            Result result = Result.success("Available market data fetched successfully", dataList);
            logger.debug("Result object to be returned: {}", result);
            return result;
        } else {
            logger.warn("No available market data found");
            return Result.error("No available market data found");
        }
    }

    // 获取一项具体的市场数据
    @GetMapping("/market")
    public ResponseEntity<Result> getMarketData(
            @RequestParam(name = "id") String instrument,
            @RequestParam(name = "timePeriod", required = false) Integer timePeriod) {
        Result result = marketDataService.getMarketDataDetail(instrument, timePeriod); // 返回包含 openPrice 的数据
        if (result.getCode() == 0) {
            return ResponseEntity.status(400).body(result);
        } else {
            return ResponseEntity.ok(result);
        }
    }
}
