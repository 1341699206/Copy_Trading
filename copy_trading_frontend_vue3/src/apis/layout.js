import httpInstance from "@/utils/http";


//获取国家信息
export function getCountriesAPI(){
    return httpInstance({
        url:'/api/register/countries'
    })
}