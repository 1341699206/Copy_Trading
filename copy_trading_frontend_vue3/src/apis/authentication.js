import request from "@/utils/http";

//a
export const login=({email,password}) =>{
    return request({
        url:'/auth/login',
        method:'POST',
        data:{
            email,
            password
        }
    })
}

//a
export const register=({username,email,password,role,createdAt,updatedAt})=>{
    return request({
        url: '/api/register',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        data: {
            username,
            email,
            password,
            role,
            createdAt,
            updatedAt,
        }
    });

}