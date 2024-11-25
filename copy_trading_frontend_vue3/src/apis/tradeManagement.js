import request from "@/utils/http";

//a 结束一项交易
export const closeTrade=({tradeId,priceClose})=>{
    const url=`/trades/${tradeId}/close`
    return request({
        url:url,
        method:'PUT',
        data:{
            tradeId,
            priceClose
        }
    })
}

//a 开启一个新的交易
export const OpenTrade=({accountId,strategyId,symbol,type,lotSize,priceOpen})=>{
    return request({
        url:'/trades/open',
        method:'POST',
        data:{
            accountId,
            strategyId,
            symbol,
            type,
            lotSize,
            priceOpen
        }
    })
}

//a 获取所有同一策略的交易
export const getTradesByStrategy=(strategyId)=>{
    const url=`/trades/strategy/${strategyId}`
    return request({
        url:url
    })
}

//a 获取所有正在进行的交易
export const getOpenTrades=(accountId)=>{
    const url=`/trades/account/${accountId}/open`
    return request({
        url:url
    })
}