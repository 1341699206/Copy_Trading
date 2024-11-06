import httpInstance from "@/utils/http";
import request from "@/utils/http";

// 获取支持的币种
export function getCurrencyAPI() {
    return httpInstance({
        url: '/account/currencies'
    });
}

// 提交账户创建
export const createTradingAccount = ({
    id,
    role,
    accountNumber, // 用户现在手动设置账户编号
    accountType,
    currency,
    balance,
    leverage = 1.0,     // 默认杠杆为 1.0
    status = 'Active'  // 默认状态为 Active
}) => {
    // 计算其他默认值
    const equity = balance;
    const realisedPNL = 0;  // 初始已实现盈亏设为 0
    const margin = 0;       // 初始保证金为 0
    const freeMargin = balance; // 初始可用保证金等于 balance
    const availableMargin = balance; // 初始可用保证金等于 balance

    const dataToSend = {
        id,
        role,
        accountNumber,
        accountType,
        currency,
        balance,
        equity,
        realisedPNL,
        margin,
        freeMargin,
        availableMargin,
        status,
        leverage
    };

    // 打印将要发送的 JSON 数据
    console.log("将要发送的 JSON 数据:", JSON.stringify(dataToSend, null, 2));

    return request({
        url: '/account/create',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        data: dataToSend
    });
};

// 获取 ROI 前 top.N 的交易者
export const getTopTraders = ({
    quantity,
    timePeriod
}) => {
    // 动态构建请求 URL
    const url = `/follower/traders?quantity=${quantity}` + (timePeriod ? `&timePeriod=${timePeriod}` : '');
    
    // 打印将要发送的请求 URL
    console.log("将要发送的请求 URL:", url);

    return request({
        url: url
    });
};

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
export const getTraderTradesHistory=({id,pageSize,currentPage})=>{
    const url=`/follower/trader/${id}/tradesHistory?pageSize=${pageSize}&currentPage=${currentPage}`
    return request({
        url:url
    })
}

// 获取某个交易的进行中的交易数据
export const getTraderOpenPosition=({id,pageSize,currentPage})=>{
    const url=`/follower/trader/${id}/openPosition?pageSize=${pageSize}&currentPage=${currentPage}`
    return request({
        url:url
    })
}

// 获取跟随该交易者的所有copiers
export const getTraderCopiers=({id,currentPage})=>{
    const url=`/follower/trader/${id}/copiers?currentPage=${currentPage}`
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
