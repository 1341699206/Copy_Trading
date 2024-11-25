import request from "@/utils/http";

//a 获取交易员的基本信息
export const getTraderStatistics=(traderId)=>{
    const url=`/stats/trader/${traderId}`
    return request({
        url:url,
    })
}

//a 获取top的交易员
export const getTopTraders=(limit)=>{
    return request({
        url:'/stats/top-traders',
        data:{
            limit
        }
    })
}

//a 获取top的跟随者
export const getTopFollowers=(limit)=>{
    return request({
        url:'/stats/top-followers',
        data:{
            limit
        }
    })
}

//a 获取跟随者
export const getFollowersStatistics=(followerId)=>{
    const url=`/stats/follower/${followerId}`
    return request({
        url:url
    })
}
