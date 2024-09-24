import hasInstance from "@/utils/http";

export function getCategory(){
    return hasInstance({
        url:'home/category/head'
    })
}