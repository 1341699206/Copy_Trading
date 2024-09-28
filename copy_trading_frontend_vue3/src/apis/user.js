import request from "@/utils/http";

export const loginAPI=({email,password,role}) =>{
    return request({
        url:'/api/login',
        method:'POST',
        data:{
            email,
            password,
            role
        }
    })
}

export const registerAPI=({name,email,password,role,country})=>{
    return request({
        url: '/api/register',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        data: {
            name,
            email,
            password,
            role,
            country
        }
    });

}