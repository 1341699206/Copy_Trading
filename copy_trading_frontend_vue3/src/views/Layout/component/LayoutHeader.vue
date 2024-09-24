<template>
    <header class="app-header">
        <div class="container">
            <h1 class="logo">
                <router-link to="/">Co-trade</router-link>
            </h1>
            <ul class="app-header-nav">
                <li class="home">
                    <router-link to="/">首页</router-link>
                </li>
                <li>
                    <router-link to="/Traders">投资者</router-link>
                </li>
                <li>
                    <router-link to="/Market">市场</router-link>
                </li>
                <li>
                    <router-link to="/Community">社区</router-link>
                </li>
            </ul>
            <div class="login">
                <router-link to="/login"><i class="iconFont icon-login"></i>登录</router-link>              
            </div>
            <div class="register">
                <router-link to="/register"><i class="iconFont icon-register"></i>注册</router-link>            
            </div>
        </div>
    </header>
</template>