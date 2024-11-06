package com.xtq_ymt.copy_trading_backend.dto;

import lombok.Data;
import java.util.List;

@Data
public class FollowerCopyDTO {
    private Long followerId; // 跟随者的唯一标识符
    private List<ProfitHistoryDTO> traderProfitHistory; // 被跟随交易者的收益历史数据
    private List<ProfitHistoryDTO> copierProfitHistory; // 复制者的收益历史数据
    private List<TraderSimpleInfDTO> copying; // 正在复制的交易者
    private List<TraderSimpleInfDTO> copied; // 已经复制过的交易者
}
