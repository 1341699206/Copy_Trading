package com.xtq_ymt.copy_trading_backend.controller;

import com.xtq_ymt.copy_trading_backend.model.TraderStats;
import com.xtq_ymt.copy_trading_backend.model.FollowerStats;
import com.xtq_ymt.copy_trading_backend.service.StatsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/stats")
@Tag(name = "Statistics Management", description = "APIs for managing statistics")
public class StatsController {

    private final StatsService statsService;

    @Autowired
    public StatsController(StatsService statsService) {
        this.statsService = statsService;
    }

    @GetMapping("/trader/{traderId}")
    @Operation(summary = "Get trader statistics", description = "Calculates and fetches statistics for a trader.")
    public ResponseEntity<TraderStats> getTraderStats(@PathVariable Long traderId) {
        TraderStats stats = statsService.calculateTraderStats(traderId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/follower/{followerId}")
    @Operation(summary = "Get follower statistics", description = "Calculates and fetches statistics for a follower.")
    public ResponseEntity<FollowerStats> getFollowerStats(@PathVariable Long followerId) {
        FollowerStats stats = statsService.calculateFollowerStats(followerId);
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/top-traders")
    @Operation(summary = "Get top traders", description = "Fetches top traders by profit.")
    public ResponseEntity<List<TraderStats>> getTopTraders(@RequestParam int limit) {
        List<TraderStats> topTraders = statsService.getTopTradersByProfit(limit);
        return ResponseEntity.ok(topTraders);
    }

    @GetMapping("/top-followers")
    @Operation(summary = "Get top followers", description = "Fetches top followers by profit.")
    public ResponseEntity<List<FollowerStats>> getTopFollowers(@RequestParam int limit) {
        List<FollowerStats> topFollowers = statsService.getTopFollowersByProfit(limit);
        return ResponseEntity.ok(topFollowers);
    }
}
