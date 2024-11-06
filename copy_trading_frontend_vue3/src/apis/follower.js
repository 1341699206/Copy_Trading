import httpInstance from "@/utils/http";
import request from "@/utils/http";

//获取支持的币种
export function getCurrencyAPI() {
    return httpInstance({
        url: '/account/currencies'
    })
}

//提交账户创建
export const createAccountAPI = ({ id, role, accountNumber, accountType, currency, balance }) => {
    return request({
        url: '/account/create',
        method: 'POST',
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
export const getTopTraders = ({ quantity, timePeriod }) => {
    // 动态构建请求 URL
    const url = `/follower/traders?quantity=${quantity}` + (timePeriod ? `&timePeriod=${timePeriod}` : '')
    return request({
        url: url
    })
}

// 获取某个交易者的信息
export const getTraderBasicInf = (id) => {
    const url = `/follower/trader/${id}`
    return request({
        url:url
    })
}

// 获取某个交易者的详细信息
export const getTraderDetailInf=({id,timePeriod}) =>{
    const url = `/follower/trader/${id}?timePeriod=${timePeriod}`
    return request({
        url:url
    })
}

// 获取某个交易的所有完成的交易数据
export const getTraderTradesHistory=({id})=>{
    const url=`/follower/trader/${id}/tradesHistory`
    return request({
        url:url
    })
}

// 获取某个交易的进行中的交易数据
export const getTraderOpenPosition=({id})=>{
    const url=`/follower/trader/${id}/openPosition`
    return request({
        url:url
    })
}

// 获取跟随该交易者的所有copiers
export const getTraderCopiers=(id)=>{
    const url=`/follower/trader/${id}/copiers`
    return request({
        url:url
    })
}

// 获取某个copier与当前trader的详细copy信息
export const getFollowerCopy=({traderId,followerId})=>{
    const url=`/follower/trader/${traderId}/copier=${followerId}`
    return request({
        url:url
    })
}
