import request from "@/utils/http";

//a 跟随行为
export const follow=({followerAccountId,traderAccountId})=>{
    return request({
        url:`/followers/follow?followerAccountId=${followerAccountId}&traderAccountId=${traderAccountId}`,
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
        url:`/followers/unfollow?followerAccountId=${followerAccountId}&traderAccountId=${traderAccountId}`,
        method:'DELETE',
        data:{
            followerAccountId,
            traderAccountId
        }
    })
}

// a 检测是否follow关系
export const checkIsFollow=({followerAccountId,traderAccountId})=>{
    const url=`/followers/check/${followerAccountId}/${traderAccountId}`
    return request({
        url:url,
        data:{
            followerAccountId,
            traderAccountId,
        }
    })
}

