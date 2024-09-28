package com.xtq_ymt.copy_trading_backend.Result;

import lombok.*;

// 通用返回结构的外部类
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;  // 1: 成功, 0: 失败
    private String msg;    // 返回消息
    private Object data;   // 返回的数据（可以是各种类型）

    // 操作成功但没有数据
    public static Result success() {
        return new Result(1, "success", null);
    }

    // 操作成功并带有数据
    public static Result success(Object data) {
        return new Result(1, "success", data);
    }

    // 操作失败并带有错误信息
    public static Result error(String msg) {
        return new Result(0, msg, null);
    }

    // 内部类 AuthResult 用于处理认证结果
    @Data
    @AllArgsConstructor
    public static class AuthResult {
        private boolean success;
        private String message;
    }

    // 内部类 Country 用于表示国家信息
    @Data
    @AllArgsConstructor
    public static class Country {
        private String code;
        private String name;
    }
}
