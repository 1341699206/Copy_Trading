package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.CopiersDTO;
import com.xtq_ymt.copy_trading_backend.dto.TraderDashboardDTO;
import org.springframework.data.domain.Page;

public interface TraderService {

    TraderDashboardDTO getTraderDashboardInfo(Long traderId, Long accountId);

    // 新增方法：获取某个 Trader 的所有 Copiers（跟随者），按收益降序排列
    Page<CopiersDTO> getTraderCopiers(Long traderId, int currentPage, int pageSize);
}
