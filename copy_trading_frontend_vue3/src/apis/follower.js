import httpInstance from "@/utils/http";
import request from "@/utils/http";

//获取支持的币种
export function getCurrencyAPI(){
    return httpInstance({
        url:'/account/currencies'
    })
}

//提交账户创建
export const createAccountAPI=({id,role,accountNumber,accountType,currency,balance})=>{
    return request({
        url:'/account/create',
        method:'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        data: {
            id,
            role,
            accountNumber,
            accountType,
            currency,
            balance
        }
    })
}

// 获取ROI 前top.N的交易者
export const getTopTraders=({quantity,timePeriod})=>{
    return request({
        url:`/traders?quantity=${quantity}&timePeriod=${timePeriod||null}`
    })
}
