import request from "@/utils/http";

//a 通过id获取交易策略
export const getStrategyById=(id)=>{
    const url=`/strategies/${id}`;
    return request({
        url:url
    })
}

//a 通过id更新交易策略
export const updateStrategyById=({id,name,description,scriptContent,isActive})=>{
    const url=`/strategies/${id}?traderId=${id}&name=${name}&description=${description}&scriptContent=${scriptContent}&isActive=${isActive}`;
    return request({
        url:url,
        method:'PUT',
        data:{
            id,
            name,
            description,
            scriptContent,
            isActive,
        }
    })
}

//a 通过id删除交易策略
export const deleteStrategyById=(id)=>{
    const url=`/strategies/${id}`;
    return request({
        url:url,
        method:'DELETE'
    })
}

//a 通过id创建新的交易策略
export const createStrategyById=({id,name,description,scriptContent})=>{
    const url=`/strategies/create?traderId=${id}&name=${name}&description=${description}&scriptContent=${scriptContent}`;
    return request({
        url:url,
        method:'POST',
        data:{
            id,
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