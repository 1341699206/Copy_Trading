package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.model.MarketData;
import com.xtq_ymt.copy_trading_backend.service.MarketDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // 表明这是一个 RESTful 控制器，处理 HTTP 请求并返回 JSON 数据。
@RequestMapping("/market-data") // 定义该控制器的基础访问路径为 "/market-data"。
@Tag(name = "Market Data", description = "APIs for managing and retrieving market data") 
// 使用 Swagger 注解为此控制器分配一个组名和描述，便于生成 API 文档。
public class MarketDataController {

    private final MarketDataService marketDataService;

    // 使用构造函数注入 MarketDataService 服务对象
    @Autowired
    public MarketDataController(MarketDataService marketDataService) {
        this.marketDataService = marketDataService;
    }

    /**
     * 获取所有市场数据
     * 
     * @return 包含所有市场数据的列表，响应格式为 HTTP 200 状态码。
     */
    @GetMapping // 定义一个 GET 请求，路径为 "/market-data"。
    @Operation(summary = "Get all market data", description = "Retrieve all available market data") 
    // Swagger 注解：为该方法生成 API 文档，指定方法摘要和详细描述。
    public ResponseEntity<List<MarketData>> getAllMarketData() {
        // 调用 MarketDataService 的 getAllMarketData 方法获取所有市场数据。
        // 将返回的结果封装为 ResponseEntity 对象，返回 HTTP 200 和数据。
        return ResponseEntity.ok(marketDataService.getAllMarketData());
    }

    /**
     * 根据交易品种符号获取最新的市场数据
     * 
     * @param symbol 交易品种符号（例如 "EUR/USD" 或 "AAPL"）
     * @return 对应交易品种的最新市场数据，响应格式为 HTTP 200 状态码。
     */
    @GetMapping("/{symbol}") // 定义一个 GET 请求，路径为 "/market-data/{symbol}"，其中 {symbol} 是动态路径参数。
    @Operation(summary = "Get latest market data for a symbol", 
               description = "Retrieve the latest market data for a specific trading symbol") 
    // Swagger 注解：为该方法生成 API 文档，指定方法摘要和详细描述。
    public ResponseEntity<MarketData> getMarketDataBySymbol(@PathVariable String symbol) {
        // 使用 @PathVariable 注解获取 URL 中的 {symbol} 参数。
        // 调用 MarketDataService 的 getLatestMarketData 方法，根据符号获取对应的最新市场数据。
        // 将返回的结果封装为 ResponseEntity 对象，返回 HTTP 200 和数据。
        return ResponseEntity.ok(marketDataService.getLatestMarketData(symbol));
    }
}
