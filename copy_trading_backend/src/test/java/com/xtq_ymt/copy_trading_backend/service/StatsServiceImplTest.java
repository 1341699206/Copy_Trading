package com.xtq_ymt.copy_trading_backend.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

public class StatsServiceImplTest {

    @Mock
    private TradeService tradeService;  // 使用 Mockito 模拟 TradeService 类

    @InjectMocks
    private StatsServiceImpl statsService;  // 自动注入被测试的 StatsServiceImpl

    /**
     * 在每个测试方法执行之前都会调用这个方法，初始化测试所需的 mock 对象。
     * 这里使用 MockitoAnnotations.openMocks 来启动并初始化 @Mock 标注的 mock 对象。
     */
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // 初始化 mock 对象
    }

    /**
     * 测试 StatsServiceImpl 中的 updateTraderAndFollowerStats 方法。
     * 该方法会调用 TradeService 中的 updateTraderAndFollowerStats 方法。
     */
    @Test
    public void testUpdateTraderAndFollowerStats() {
        Long tradeId = 1L;  // 模拟传入的交易 ID

        // 设置 mock 对象的行为：当调用 tradeService.updateTraderAndFollowerStats(tradeId) 时，什么也不做
        doNothing().when(tradeService).updateTraderAndFollowerStats(tradeId);

        // 调用 StatsServiceImpl 的方法，实际上会触发对 TradeService 的调用
        statsService.updateTraderAndFollowerStats(tradeId);

        // 验证 tradeService 的 updateTraderAndFollowerStats 方法被调用了一次
        verify(tradeService, times(1)).updateTraderAndFollowerStats(tradeId);
    }
}
