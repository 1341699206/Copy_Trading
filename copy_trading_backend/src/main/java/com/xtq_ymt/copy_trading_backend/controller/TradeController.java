package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.dto.ClosePositionRequest;
import com.xtq_ymt.copy_trading_backend.dto.OpenPositionRequest;
import com.xtq_ymt.copy_trading_backend.model.Trade;
import com.xtq_ymt.copy_trading_backend.service.TradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/trade")
public class TradeController {

    @Autowired
    private TradeService tradeService;

    @PostMapping(path = "/open", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Trade> openPosition(@RequestBody OpenPositionRequest request) {
        try {
            Trade trade = tradeService.openPosition(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(trade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PostMapping(path = "/close", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Trade> closePosition(@RequestBody ClosePositionRequest request) {
        try {
            Trade trade = tradeService.closePosition(request);
            return ResponseEntity.ok(trade);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}