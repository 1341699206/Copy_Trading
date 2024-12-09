package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.model.Strategy;
import com.xtq_ymt.copy_trading_backend.service.StrategyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 策略管理控制器，提供基于交易员ID操作策略的API接口
 */
@RestController
@RequestMapping("/strategies")
@Tag(name = "Strategy Management", description = "APIs for managing trading strategies using trader ID")
public class StrategyController {

    private final StrategyService strategyService;

    @Autowired
    public StrategyController(StrategyService strategyService) {
        this.strategyService = strategyService;
    }

    /**
     * 创建新的交易策略
     *
     * @param traderId 交易员ID
     * @param name 策略名称
     * @param description 策略描述
     * @param scriptContent 策略脚本内容
     * @return 创建的策略对象及HTTP状态201
     */
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

    /**
     * 更新现有的交易策略
     *
     * @param traderId 交易员ID
     * @param name 策略名称
     * @param description 策略描述
     * @param scriptContent 策略脚本内容
     * @param isActive 策略是否启用
     * @return 更新后的策略对象及HTTP状态200
     */
    @PutMapping("/update")
    @Operation(summary = "Update a strategy", description = "Updates an existing trading strategy using trader ID.")
    public ResponseEntity<Strategy> updateStrategy(
            @RequestParam Long traderId,
            @RequestParam String name,
            @RequestParam String description,
            @RequestParam String scriptContent,
            @RequestParam boolean isActive) {
        Strategy updatedStrategy = strategyService.updateStrategyByTraderId(traderId, name, description, scriptContent, isActive);
        return new ResponseEntity<>(updatedStrategy, HttpStatus.OK);
    }

    /**
     * 删除现有的交易策略
     *
     * @param traderId 交易员ID
     * @return 成功删除的消息及HTTP状态200
     */
    @DeleteMapping("/delete")
    @Operation(summary = "Delete a strategy", description = "Deletes an existing trading strategy using trader ID.")
    public ResponseEntity<String> deleteStrategy(@RequestParam Long traderId) {
        strategyService.deleteStrategyByTraderId(traderId);
        return new ResponseEntity<>("策略删除成功", HttpStatus.OK);
    }

    /**
     * 根据交易员ID获取策略详情
     *
     * @param traderId 交易员ID
     * @return 策略对象及HTTP状态200
     */
    @GetMapping("/trader/{traderId}")
    @Operation(summary = "Get strategy by trader ID", description = "Fetches the strategy for a specific trader.")
    public ResponseEntity<Strategy> getStrategyByTraderId(@PathVariable Long traderId) {
        Strategy strategy = strategyService.getStrategyByTraderId(traderId);
        return new ResponseEntity<>(strategy, HttpStatus.OK);
    }

    /**
     * 根据交易员ID获取策略ID
     *
     * @param traderId 交易员ID
     * @return 策略ID及HTTP状态200
     */
    @GetMapping("/id")
    @Operation(summary = "Get strategy ID by trader ID", description = "Fetches the strategy ID for a specific trader.")
    public ResponseEntity<Long> getStrategyIdByTraderId(@RequestParam Long traderId) {
        Long strategyId = strategyService.getStrategyIdByUserId(traderId);
        return ResponseEntity.ok(strategyId);
    }
}
