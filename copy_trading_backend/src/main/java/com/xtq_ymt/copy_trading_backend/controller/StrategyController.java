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

/**
 * 策略管理控制器，提供管理交易策略的API接口
 */
@RestController
@RequestMapping("/strategies")  // 基础路径为 /strategies
@Tag(name = "Strategy Management", description = "APIs for managing trading strategies")
public class StrategyController {

    private final StrategyService strategyService;  // 策略服务的依赖注入

    @Autowired
    public StrategyController(StrategyService strategyService) {
        this.strategyService = strategyService;  // 初始化策略服务
    }

    /**
     * 创建新的交易策略
     * @param traderId 交易员ID
     * @param name 策略名称
     * @param description 策略描述
     * @param scriptContent 策略脚本内容
     * @return 创建的策略对象及HTTP状态201
     */
    @PostMapping("/create")
    @Operation(summary = "Create a new strategy", description = "Creates a new trading strategy for a trader.")
    public ResponseEntity<Strategy> createStrategy(
            @RequestParam Long traderId,  // 交易员ID
            @RequestParam String name,  // 策略名称
            @RequestParam String description,  // 策略描述
            @RequestParam String scriptContent) {  // 策略脚本内容
        Strategy strategy = strategyService.createStrategy(traderId, name, description, scriptContent);
        return new ResponseEntity<>(strategy, HttpStatus.CREATED);  // 返回创建的策略及状态201
    }

    /**
     * 更新现有的交易策略
     * @param id 策略ID
     * @param name 策略名称
     * @param description 策略描述
     * @param scriptContent 策略脚本内容
     * @param isActive 策略是否启用
     * @return 更新后的策略对象及HTTP状态200
     */
    @PutMapping("/{id}")  // 路径中的 {id} 是策略的ID
    @Operation(summary = "Update a strategy", description = "Updates an existing trading strategy.")
    public ResponseEntity<Strategy> updateStrategy(
            @PathVariable Long id,  // 策略ID
            @RequestParam String name,  // 策略名称
            @RequestParam String description,  // 策略描述
            @RequestParam String scriptContent,  // 策略脚本内容
            @RequestParam boolean isActive) {  // 策略是否启用
        Strategy updatedStrategy = strategyService.updateStrategy(id, name, description, scriptContent, isActive);
        return new ResponseEntity<>(updatedStrategy, HttpStatus.OK);  // 返回更新后的策略及状态200
    }

    /**
     * 删除现有的交易策略
     * @param id 策略ID
     * @return 成功删除的消息及HTTP状态200
     */
    @DeleteMapping("/{id}")  // 路径中的 {id} 是策略的ID
    @Operation(summary = "Delete a strategy", description = "Deletes an existing trading strategy.")
    public ResponseEntity<String> deleteStrategy(@PathVariable Long id) {  // 策略ID
        strategyService.deleteStrategy(id);
        return new ResponseEntity<>("Strategy deleted successfully", HttpStatus.OK);  // 返回成功消息及状态200
    }

    /**
     * 根据交易员ID获取所有策略
     * @param traderId 交易员ID
     * @return 交易员的策略列表及HTTP状态200
     */
    @GetMapping("/trader/{traderId}")  // 路径中的 {traderId} 是交易员的ID
    @Operation(summary = "Get strategies by trader ID", description = "Fetches all strategies for a specific trader.")
    public ResponseEntity<List<Strategy>> getStrategiesByTraderId(@PathVariable Long traderId) {  // 交易员ID
        List<Strategy> strategies = strategyService.getStrategiesByTraderId(traderId);
        return new ResponseEntity<>(strategies, HttpStatus.OK);  // 返回策略列表及状态200
    }

    /**
     * 根据策略ID获取策略详情
     * @param id 策略ID
     * @return 策略对象及HTTP状态200
     */
    @GetMapping("/{id}")  // 路径中的 {id} 是策略的ID
    @Operation(summary = "Get strategy by ID", description = "Fetches the details of a specific strategy by ID.")
    public ResponseEntity<Strategy> getStrategyById(@PathVariable Long id) {  // 策略ID
        Strategy strategy = strategyService.getStrategyById(id);
        return new ResponseEntity<>(strategy, HttpStatus.OK);  // 返回策略对象及状态200
    }
}