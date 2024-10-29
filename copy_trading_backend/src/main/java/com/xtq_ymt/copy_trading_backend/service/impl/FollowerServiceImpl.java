package com.xtq_ymt.copy_trading_backend.service.impl;

import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.dto.TradersDataDTO;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.repository.TraderRepository;
import com.xtq_ymt.copy_trading_backend.service.FollowerService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class FollowerServiceImpl implements FollowerService {

    @Autowired
    private TraderRepository traderRepository;

    public Result getTopTradersData(Integer quantity, Integer timePeriod) {
        // 判断是否有时间限制，
        Pageable pageable = PageRequest.of(0, quantity); // 返回前 n 个交易者
        List<Trader> traders;
        if (timePeriod == null || timePeriod == 0) {
            traders = traderRepository.findTopByOrderByROIDesc(pageable);
        } else {
            
            // 查询历史的情况
            LocalDate startDate = LocalDate.now().minusDays(timePeriod);// 当前日期减去指定天数
            LocalDate endDate = LocalDate.now();// 当前时间（结束时间）

            // 未处理的待定逻辑
            // traders = traderRepository.findTopByOrderByROIDesc(startDate, endDate, pageable);
            traders=traderRepository.findTopByOrderByROIDesc(pageable);
        }

        // 导入DTO
        List<TradersDataDTO> topTraders = new ArrayList<>();
        for (Trader trader : traders) {
            TradersDataDTO traderData = new TradersDataDTO(trader.getTraderId(), trader.getName(), trader.getROI(),
                    trader.getFollowers(), trader.getFollowersWhoFavorited().size(), trader.getPerformance());
            topTraders.add(traderData);
        }

        //返回查询得到数据
        return Result.success("Getting traders succeeded.", topTraders);
    }
}
