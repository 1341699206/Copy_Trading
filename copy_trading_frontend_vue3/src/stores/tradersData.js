import { defineStore } from "pinia";
import { ref } from "vue";
import { getTopTraders } from "@/apis/statisticsManagement";


export const useTradersDataStore = defineStore('traderData', () => {
    // 定义trader数据的state
    //top traders
    const tradersData = ref({});

    // 定义获取接口数据的 action 函数
    //获取top traders
    const getTopTradersData = async ( limit ) => {
        const res = await getTopTraders(limit)
        tradersData.value = res;
    }
    const getTopOneYearTradersData = async ( quantity ) => {
        const year=365;
        const res = await getTopTraders({ quantity:quantity,timePeriod:year})
        tradersData.value = res;
    }
    const getMonthRisingTradersData = async ( quantity ) => {
        const month=30;
        const res = await getTopTraders({ quantity,timePeriod:month })
        tradersData.value = res;
    }

    // 返回 state 和 action
    return{
        tradersData,
        getTopTradersData,
        getTopOneYearTradersData,
        getMonthRisingTradersData
    }
})