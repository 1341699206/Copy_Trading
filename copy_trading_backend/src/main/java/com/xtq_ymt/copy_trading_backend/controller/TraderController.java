package com.xtq_ymt.copy_trading_backend.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import com.xtq_ymt.copy_trading_backend.service.TraderService;
import com.xtq_ymt.copy_trading_backend.dto.TraderDashboardDTO;

@RestController
@RequestMapping("/api/traders")
public class TraderController {
    @Autowired
    private TraderService traderService;

    // 更新后的方法，支持 traderId 和 accountId
    @GetMapping("/{traderId}/dashboard-info/{accountId}")
    public ResponseEntity<TraderDashboardDTO> getTraderDashboardInfo(
            @PathVariable Long traderId, @PathVariable Long accountId) {
        TraderDashboardDTO dashboardInfo = traderService.getTraderDashboardInfo(traderId, accountId);
        return ResponseEntity.ok(dashboardInfo);
    }
}
