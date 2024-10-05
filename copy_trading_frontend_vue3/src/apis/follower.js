import httpInstance from "@/utils/http";

//获取支持的币种
export function getCurrencyAPI(){
    return httpInstance({
        url:'/account/currencies'
    })
}