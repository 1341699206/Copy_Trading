package com.xtq_ymt.copy_trading_backend.service.impl;

import com.xtq_ymt.copy_trading_backend.dto.FollowerCopyDTO;
import com.xtq_ymt.copy_trading_backend.service.FollowerChartService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Service
public class FollowerChartServiceImpl implements FollowerChartService {

    @Override
    public String getFollowedChartData(Long traderId, Long followerId) {
        // 构建 JSON 文件的路径，使用硬编码的路径
        String filePath = "D:/zulutrade_like/copy-trade_project_root/zulutrade_data/zulutrade_data/zulutrade_data/follower/followed_chart/" + traderId + "_" + followerId + ".json";
        try {
            // 读取 JSON 文件内容
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("无法读取文件: " + filePath, e);
        }
    }

    @Override
    public FollowerCopyDTO getFollowerCopyInfo(Long traderId, Long followerId) {
        // 暂时未实现此功能，之后可以根据需求实现
        return null;
    }
}
