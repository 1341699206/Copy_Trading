import request from "@/utils/http";

//a
export const login=({username,password}) =>{
    return request({
        url:`/auth/login?username=${username}&password=${password}`,
        method:'POST',
        data:{
            username,
            password,
        }
    })
}

//a
export const register=({username,email,password,role})=>{
    return request({
        url: '/auth/register',
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        data: {
            username,
            email,
            password,
            role,
        }
    });

}