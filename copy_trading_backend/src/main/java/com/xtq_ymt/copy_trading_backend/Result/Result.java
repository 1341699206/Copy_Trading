package com.xtq_ymt.copy_trading_backend.Result;

import lombok.*;

// 标准响应结果
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;  // 1: 成功, 0: 失败
    private String msg;    // 返回消息
    private Object data;   // 返回的数据（可以是各种类型）

    // 操作成功但没有数据
    public static Result success(String msg) {
        return new Result(1,msg,null);
    }

    // 操作成功并带有数据
    public static Result success(String msg,Object data) {
        return new Result(1, msg, data);
    }

    // 操作失败并带有错误信息
    public static Result error(String msg) {
        return new Result(0, msg, msg);
    }
}
