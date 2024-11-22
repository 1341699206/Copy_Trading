package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.service.TradeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trades")
@Tag(name = "Trade Management", description = "APIs for managing trades")
public class TradeController {

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @PostMapping("/open")
    @Operation(summary = "Open a new trade", description = "Opens a new trade for a specific account and strategy.")
    public ResponseEntity<Trade> openTrade(
            @RequestParam Long accountId,
            @RequestParam Long strategyId,
            @RequestParam String symbol,
            @RequestParam String type,
            @RequestParam double lotSize,
            @RequestParam double priceOpen) {
        Trade trade = tradeService.openTrade(accountId, strategyId, symbol, type, lotSize, priceOpen);
        return new ResponseEntity<>(trade, HttpStatus.CREATED);
    }

    @PutMapping("/{tradeId}/close")
    @Operation(summary = "Close a trade", description = "Closes an existing trade.")
    public ResponseEntity<Trade> closeTrade(@PathVariable Long tradeId, @RequestParam double priceClose) {
        Trade trade = tradeService.closeTrade(tradeId, priceClose);
        return new ResponseEntity<>(trade, HttpStatus.OK);
    }

    @GetMapping("/account/{accountId}/open")
    @Operation(summary = "Get open trades", description = "Fetches all open trades for a specific account.")
    public ResponseEntity<List<Trade>> getOpenTrades(@PathVariable Long accountId) {
        List<Trade> trades = tradeService.getOpenTradesByAccountId(accountId);
        return new ResponseEntity<>(trades, HttpStatus.OK);
    }

    @GetMapping("/strategy/{strategyId}")
    @Operation(summary = "Get trades by strategy", description = "Fetches all trades for a specific strategy.")
    public ResponseEntity<List<Trade>> getTradesByStrategy(@PathVariable Long strategyId) {
        List<Trade> trades = tradeService.getTradesByStrategyId(strategyId);
        return new ResponseEntity<>(trades, HttpStatus.OK);
    }
}
