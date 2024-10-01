<script setup>
import {onMounted,reactive,ref} from 'vue'
import {getCurrencyAPI} from '@/apis/follower'

  defineProps({
    show:{
      type: Boolean,
      default: false
    }
  })

//账户信息
const accountInfo=reactive({
    accountNumber:"",
    accountType:"",
    currency:"",

})

//限制规则
const rules={
    accountNumber:[
        {required: true, message:" account number cannot be empty. "}
    ],
    accountType:[
        {required: true,message:" account type cannot be empty. "}
    ],
    currency:[
        {required: true,message:" currency cannot be empty. "}
    ]
}

// 从后端获取币种
const currencies = ref([]);
const getCurrency = async () => {
  const res = await getCurrencyAPI();
  currencies.value = res.data;
};

// 启动函数
onMounted(() => {
  getCurrency();
});

</script>


<template>
  <el-dialog :model-value="show" title="New Account" width="500">
    <el-form :model="accountInfo" :rules="rules">

      <!-- 账户编号 -->
      <el-form-item label="account number" prop="accountNumber">
        <el-input v-model="accountInfo.accountNumber" autocomplete="off" />
      </el-form-item>
      
      <!-- 账户类型 -->
      <el-form-item label="account type" prop="accountType">
        <el-select v-model="accountInfo.accountType" placeholder="Please set account type">
          <el-option label="Simulated account" />
          <el-option label="Real account" />
        </el-select>
      </el-form-item>
      
      <!-- 账户币种 -->
      <el-form-item prop="currency" label="currency">
        <el-select v-model="accountInfo.currency" placeholder="Please set currency">
          <el-option v-for="currency in currencies" :key="currency.code" :value="currency.code">
            {{ currency.name }}
          </el-option>
        </el-select>
      </el-form-item>
      
    </el-form>
    <template #footer>
      <div class="dialog-footer">
        <el-button >Cancel</el-button>
        <el-button type="primary">
          Confirm
        </el-button>
      </div>
    </template>
  </el-dialog>
</template>
