import { defineStore } from "pinia";
import { ref } from "vue";
import { getStrategyById, updateStrategyById, createStrategyById, deleteStrategyById, getStrategyByTraderId } from "@/apis/strategyManagement";

export const useStrategyStore = defineStore('strategy', () => {
    const strategyInfo = ref({})

    const getStrategyByTrader = async (traderId) => {
        try {
            const res = await getStrategyByTraderId(traderId);
            strategyInfo.value = res[0];
        } catch (error) {
            console.error("Failed to fetch strategy info:", error);
        }

    }

    const getStrategyInfo = async (strategyId) => {
        try {
            const res = await getStrategyById(strategyId);
            strategyInfo.value = res;
        } catch (error) {
            console.error("Failed to fetch strategy info:", error);
        }
    }

    const updateStrategy = async ({ strategyId, name, description, scriptContent, isActive }) => {
        try {
            const res = await updateStrategyById({ strategyId, name, description, scriptContent, isActive })
            strategyInfo.value = res
        } catch (error) {
            console.error("Failed to update strategy:", error)
        }
    }

    const createStrategy = async ({ traderId, name, description, scriptContent }) => {
        try {
            const res = await createStrategyById({ traderId, name, description, scriptContent })
            strategyInfo.value = res
        } catch (error) {
            console.error("Failed to create strategy:", error)
        }
    }

    const deleteStrategy = async (strategyId) => {
        try {
            const res = await deleteStrategyById(strategyId);
            strategyInfo.value = res;
        } catch (error) {
            console.error("Failed to delete strategy:", error);
        }
    }

    return {
        strategyInfo,
        getStrategyByTrader,
        getStrategyInfo,
        updateStrategy,
        createStrategy,
        deleteStrategy,
    };
}, {
    persist: {
        key: 'strategy', // 自定义存储的键名
        storage: sessionStorage // 指定存储方式为 sessionStorage
    }
})
