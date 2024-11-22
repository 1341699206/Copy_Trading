package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.model.Strategy;
import com.xtq_ymt.copy_trading_backend.service.StrategyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/strategies")
@Tag(name = "Strategy Management", description = "APIs for managing trading strategies")
public class StrategyController {

    private final StrategyService strategyService;

    @Autowired
    public StrategyController(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    @PostMapping("/create")
    @Operation(summary = "Create a new strategy", description = "Creates a new trading strategy for a trader.")
    public ResponseEntity<Strategy> createStrategy(
            @RequestParam Long traderId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String scriptContent) {
        Strategy strategy = strategyService.createStrategy(traderId, name, description, scriptContent);
        return new ResponseEntity<>(strategy, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a strategy", description = "Updates an existing trading strategy.")
    public ResponseEntity<Strategy> updateStrategy(
            @PathVariable Long id,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String scriptContent,
            @RequestParam boolean isActive) {
        Strategy updatedStrategy = strategyService.updateStrategy(id, name, description, scriptContent, isActive);
        return new ResponseEntity<>(updatedStrategy, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a strategy", description = "Deletes an existing trading strategy.")
    public ResponseEntity<String> deleteStrategy(@PathVariable Long id) {
        strategyService.deleteStrategy(id);
        return new ResponseEntity<>("Strategy deleted successfully", HttpStatus.OK);
    }

    @GetMapping("/trader/{traderId}")
    @Operation(summary = "Get strategies by trader ID", description = "Fetches all strategies for a specific trader.")
    public ResponseEntity<List<Strategy>> getStrategiesByTraderId(@PathVariable Long traderId) {
        List<Strategy> strategies = strategyService.getStrategiesByTraderId(traderId);
        return new ResponseEntity<>(strategies, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get strategy by ID", description = "Fetches the details of a specific strategy by ID.")
    public ResponseEntity<Strategy> getStrategyById(@PathVariable Long id) {
        Strategy strategy = strategyService.getStrategyById(id);
        return new ResponseEntity<>(strategy, HttpStatus.OK);
    }
}
