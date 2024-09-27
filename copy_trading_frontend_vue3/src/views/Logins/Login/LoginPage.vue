
<script setup>
  import { ref } from 'vue'
  import { loginAPI } from '@/apis/user'
  import { ElMessage } from 'element-plus'
  import 'element-plus/theme-chalk/el-message.css'
  import { useRouter} from 'vue-router'

  //表单对象
  const userInfo = ref({
    email:'',
    password:'',
    role:'Follower',
  })

  // 规则数据对象
  const rules = {
    email: [
      { required: true, message: 'Email cannot be empty' }
    ],
    password: [
      { required: true, message: 'Password cannot be empty.' },
      { min: 6, max: 14, message: 'Password length requires 6-14 characters' }
    ],
    role:[
      {required: true}
    ],
  }
  

  const formRef =ref(null)
  const router=useRouter()
  const doLogin =()=>{
    //调用实例方法
    formRef.value.validate(async (valid)=>{
      if(valid){
        //TODO LOGIN
        await loginAPI(userInfo)
        ElMessage({type:'success',message:'Login successful!'})
        // 根据用户选择的角色跳转到相应的页面
        if (userInfo.value.role === "TRADER") {
          router.replace('/trader');
        } else if (userInfo.value.role === "FOLLOWER") {
          router.replace('/traders');
        } else if (userInfo.value.role === "ADMIN") {
          router.replace('/admin');
        }
      }
    })
  }
</script>

<template>
  <section class="login-section">
    <div class="wrapper">
      <nav>
        <h1>Log in</h1>
      </nav>
      <div class="account-box">
        <div class="form">
          <el-form ref="formRef" :model="userInfo" :rules="rules" label-position="right" label-width="60px" status-icon>
            <el-form-item prop="email" label="email">
              <el-input v-model="userInfo.email" />
            </el-form-item>
            <el-form-item prop="password" label="password">
              <el-input v-model="userInfo.password" />
            </el-form-item>
            <el-form-item prop="role" label="role">
              <el-select v-model="userInfo.role" placeholder="role">
                <el-option  value="Trader" />
                <el-option  value="Follower" />
                <el-option  value="Admin" />
              </el-select>
            </el-form-item>

            <el-button  class="loginB" @click="doLogin">login</el-button>
            <el-button  class="register" @click="$router.push('/register')">register</el-button>
          </el-form>
        </div>
      </div>
    </div>
  </section>
</template>

<style scoped lang='scss'>
.login-section {
  //background: url('@/assets/images/login-bg.png') no-repeat center / cover;
  height: 488px;
  position: relative;

  .wrapper {
    width: 380px;
    background: #fff;
    position: absolute;
    left: 50%;
    top: 24%;
    transform: translate3d(100px, 0, 0);
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.15);

    nav {
      font-size: 14px;
      height: 55px;
      margin-bottom: 20px;
      border-bottom: 1px solid #f5f5f5;
      display: flex;
      padding: 0 40px;
      text-align: right;
      align-items: center;

      h1 {
        flex: 1;
        line-height: 1;
        display: inline-block;
        font-size: 18px;
        position: relative;
        text-align: center;
      }
    }
  }
}

.account-box {
  .toggle {
    padding: 15px 40px;
    text-align: right;

    a {
      color: $xtxColor;

      i {
        font-size: 14px;
      }
    }
  }

  .form {
    padding: 0 20px 20px 20px;

    &-item {
      margin-bottom: 28px;

      .input {
        position: relative;
        height: 36px;

        >i {
          width: 34px;
          height: 34px;
          background: #cfcdcd;
          color: #fff;
          position: absolute;
          left: 1px;
          top: 1px;
          text-align: center;
          line-height: 34px;
          font-size: 18px;
        }

        input {
          padding-left: 44px;
          border: 1px solid #cfcdcd;
          height: 36px;
          line-height: 36px;
          width: 100%;

          &.error {
            border-color: $priceColor;
          }

          &.active,
          &:focus {
            border-color: $xtxColor;
          }
        }

        .code {
          position: absolute;
          right: 1px;
          top: 1px;
          text-align: center;
          line-height: 34px;
          font-size: 14px;
          background: #f5f5f5;
          color: #666;
          width: 90px;
          height: 34px;
          cursor: pointer;
        }
      }

      >.error {
        position: absolute;
        font-size: 12px;
        line-height: 28px;
        color: $priceColor;

        i {
          font-size: 14px;
          margin-right: 2px;
        }
      }
    }

    .btn {
      display: block;
      width: 100%;
      height: 40px;
      color: #fff;
      text-align: center;
      line-height: 40px;
      background: $xtxColor;

      &.disabled {
        background: #cfcdcd;
      }
    }
  }

  .action {
    padding: 20px 40px;
    display: flex;
    justify-content: space-between;
    align-items: center;

    .url {
      a {
        color: #999;
        margin-left: 10px;
      }
    }
  }
}

.loginB {
  background: $xtxColor;
  width: 40%;
  color: #fff;
}

.registerB {
  width: 40%;
}
</style>
