package com.xtq_ymt.copy_trading_backend.service.impl;

import java.io.IOException;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xtq_ymt.copy_trading_backend.Result.Result;
import com.xtq_ymt.copy_trading_backend.model.Follower;
import com.xtq_ymt.copy_trading_backend.model.Trader;
import com.xtq_ymt.copy_trading_backend.service.UserService;
import com.xtq_ymt.copy_trading_backend.service.WebSocketManager;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserServiceImpl implements UserService{

     private ObjectMapper objectMapper = new ObjectMapper();
    
    @Override
    public void sendFollowerUpdateToFrontEnd(Follower follower) {
        try {
            // 将 user 对象转为 JSON 格式的消息
            Result result=Result.success("userInfoUpdate", follower);
            String userUpdateMessage = objectMapper.writeValueAsString(result);

            log.info("Sending follower update to user {}: {}", follower.getFollowerId(), userUpdateMessage);
            
            // 向指定用户发送消息
            WebSocketManager.sendMessageToUser( "FOLLOWER:"+String.valueOf(follower.getFollowerId()), userUpdateMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendTraderUpdateToFrontEnd(Trader trader) {
        try {
            // 将 user 对象转为 JSON 格式的消息
            Result result=Result.success("userInfoUpdate", trader);
            String userUpdateMessage = objectMapper.writeValueAsString(result);
            
            // 向指定用户发送消息
            WebSocketManager.sendMessageToUser( "TRADER:"+String.valueOf(trader.getTraderId()), userUpdateMessage);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
