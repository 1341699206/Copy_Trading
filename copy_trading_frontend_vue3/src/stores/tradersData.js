import { defineStore } from "pinia";
import { ref } from "vue";
import { getTopTraders } from "@/apis/follower";


export const useTraderDataStore = defineStore('traderData', () => {
    // 定义trader数据的state
    //top traders
    const tradersData = ref({});

    // 定义获取接口数据的 action 函数
    //获取top traders
    const getTopTradersData = async ({ quantity }) => {
        const res = await getTopTraders({ quantity })
        tradersData.value = res.data;
    }
    const getTopOneYearTradersData = async ({ quantity }) => {
        const year=365;
        const res = await getTopTraders({ quantity,year})
        tradersData.value = res.data;
    }
    const getMonthRisingTradersData = async ({ quantity }) => {
        const month=30;
        const res = await getTopTraders({ quantity,month })
        tradersData.value = res.data;
    }

    // 返回 state 和 action
    return{
        tradersData,
        getTopTradersData,
        getTopOneYearTradersData,
        getMonthRisingTradersData
    }
})