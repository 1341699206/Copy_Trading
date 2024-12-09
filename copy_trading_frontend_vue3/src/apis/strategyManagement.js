import request from "@/utils/http";

//a 通过traderId获取strategyId
export const getStrategyIdByTradeId=(traderId)=>{
    const url=`/strategies/${traderId}`;
    return request({
        url:url
    })
}

//a 通过id更新交易策略
export const updateStrategyById=({traderId,name,description,scriptContent,isActive})=>{
    const url=`/strategies/${traderId}?traderId=${traderId}&name=${name}&description=${description}&scriptContent=${scriptContent}&isActive=${isActive}`;
    return request({
        url:url,
        method:'PUT',
        data:{
            traderId,
            name,
            description,
            scriptContent,
            isActive,
        }
    })
}

//a 通过id删除交易策略
export const deleteStrategyById=(traderId)=>{
    const url=`/strategies/${traderId}`;
    return request({
        url:url,
        method:'DELETE'
    })
}

//a 通过id创建新的交易策略
export const createStrategyById=({traderId,name,description,scriptContent})=>{
    const url=`/strategies/create?traderId=${traderId}&name=${name}&description=${description}&scriptContent=${scriptContent}`;
    return request({
        url:url,
        method:'POST',
        data:{
            traderId,
            name,
            description,
            scriptContent,
        }
    })
}

//a 通过traderId获取trader的交易策略
export const getStrategyByTraderId=(traderId)=>{
    const url=`/strategies/trader/${traderId}`;
    return request({
        url:url,
        method:'GET'
    })
}