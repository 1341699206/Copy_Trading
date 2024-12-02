import { defineStore } from "pinia";
import { ref } from 'vue';
import { updateAccount,getAccountDetails } from "@/apis/accountManagement";

export const useAccountStore =defineStore('account',()=>{
    const accountInfo=ref({})

    const getAccountInfo =async (id)=>{
        try {
            const res = await getAccountDetails(id);
            if(!res) throw new Error("Account need to be created!")
            // 将其他返回的数据存储到 accountInfo 中
            accountInfo.value = res;
        } catch (error) {
            console.error("Failed to fetch account info:", error);
            throw error;
        }
    }

    const updateAccountInfo =async (account)=>{
        try{
            const res=await updateAccount(account)
            accountInfo.value=res
        }catch(error){
            console.error("Failed to update account info:", error);
        }
    }

    return{
        accountInfo,
        getAccountInfo,
        updateAccountInfo
    };
}, {
    persist: {
        key: 'account', // 自定义存储的键名
        storage: sessionStorage // 指定存储方式为 sessionStorage
    }
})