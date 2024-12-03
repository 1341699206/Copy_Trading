import request from "@/utils/http";

//a 更新账户信息
export const updateAccount=(account)=>{
    return request({
        url:'/accounts/update',
        method:'PUT',
        data:account
    })
}

//a 创建新账户
export const createAccount=({userId,initialBalance})=>{
    return request({
        url:`/accounts/create?userId=${userId}&initialBalance=${initialBalance}`,
        method:'POST',
        data:{
            userId,
            initialBalance
        }
    })
}

//a 获取账户信息
export const getAccountDetails=(userId)=>{
    const url=`/accounts/${userId}`
    return request({
        url:url,
    })
}