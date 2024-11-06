package com.xtq_ymt.copy_trading_backend.service;

import com.xtq_ymt.copy_trading_backend.dto.TraderDashboardDTO;

public interface TraderService {
    TraderDashboardDTO getTraderDashboardInfo(Long traderId, Long accountId);
}
