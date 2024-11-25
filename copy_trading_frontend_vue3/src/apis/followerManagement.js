import request from "@/utils/http";

//a 跟随行为
export const follow=({followerAccountId,traderAccountId})=>{
    return request({
        url:`/followers/follow`,
        method:'POST',
        data:{
            followerAccountId,
            traderAccountId
        }
    })
}

//a 获取正在跟随的交易员
export const getFollowedTraders=(followerAccountId)=>{
    const url=`/followers/${followerAccountId}/traders`
    return request({
        url:url
    })
}

//a 取消对跟随交易员
export const unfollow=({followerAccountId,traderAccountId})=>{
    return request({
        url:'/followers/unfollow',
        method:'DELETE',
        data:{
            followerAccountId,
            traderAccountId
        }
    })
}

// 获取 ROI 前 top.N 的交易者
export const getTopTraders = ({
    quantity,
    timePeriod
}) => {
    // 动态构建请求 URL
    const url = `/follower/traders?quantity=${quantity}` + (timePeriod ? `&timePeriod=${timePeriod}` : '');

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
