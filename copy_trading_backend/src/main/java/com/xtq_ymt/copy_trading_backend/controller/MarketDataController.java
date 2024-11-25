package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.model.MarketData;
import com.xtq_ymt.copy_trading_backend.service.MarketDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/market-data")
@Tag(name = "Market Data", description = "APIs for managing and retrieving market data")
public class MarketDataController {

    private final MarketDataService marketDataService;

    @Autowired
    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    @GetMapping
    @Operation(summary = "Get all market data", description = "Retrieve all available market data")
    public ResponseEntity<List<MarketData>> getAllMarketData() {
        return ResponseEntity.ok(marketDataService.getAllMarketData());
    }

    @GetMapping("/{symbol}")
    @Operation(summary = "Get latest market data for a symbol", description = "Retrieve the latest market data for a specific trading symbol")
    public ResponseEntity<MarketData> getMarketDataBySymbol(@PathVariable String symbol) {
        return ResponseEntity.ok(marketDataService.getLatestMarketData(symbol));
    }
}
