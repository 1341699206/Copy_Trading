# Copying trade 接口文档-V1.0

## 1. 主体页面管理

## 1.1 交易员(trader)页面(zulu 的 leader 页面)

### 1.1.1 交易员页面主体

#### 1.1.1.1 基本信息

> 请求路径：/trader
>
> 请求方式：GET
>
> 接口描述：该接口用于获取 收益率为正，年最佳表现前 5，小额收益前 5，复制金额前 5，2 年内收益率为正，最近 1 月收益为正的各项的 trader 的降序。

#### 1.1.1.2 请求参数

无

#### 1.1.1.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：(avatarUrl 是头像的存储地址)

```json
{
  "code": 1,
  "msg": "success",
  "data": [
    "topPerforming":[
		{
      		"traderId":1,
      		"name": "xxx",
      		"avatarUrl": "http://",
      		"copiers": 2,
      		"followers": 2,
      		"ROI": 22.22%;
      		"performance": "json"
      	}
	],
	"topPerforming1Year":[
		{
      		"traderId":1,
      		"name": "xxx",
            "avatarUrl": "http://",
      		"ROI": 22.22%
      	}
	],
	"Under$1000":[
       {
          "traderId":1,
      		"name": "xxx",
            "avatarUrl": "http://",
      		"ROI": 22.22%
       }
    ],
	"amountCopying":[
		{
      		"traderId":1,
      		"name": "xxx",
            "avatarUrl": "http://",
      		"ROI": 22.22%
      	}
	],
	"topPerforming2Year":[
    	{
            "traderId":1,
      		"name": "xxx",
            "avatarUrl": "http://",
      		"copiers": 2,
      		"followers": 2,
            "ROI": 22.22%,
      		"performance": "json"
		}
    ],
	"MonthlyRisingStars":[
    	{
            "traderId":1,
      		"name": "xxx",
            "avatarUrl": "http://",
      		"copiers": 2,
      		"followers": 2,
            "ROI": 22.22%,
      		"performance": "json"
		}
    ]
  ]
}
```

### 1.1.2 查询各类条件下的traders

#### 1.1.2.1 基本信息

> 请求路径：/trader/list
>
> 请求方式：GET
>
> 接口描述：该接口用于查询各类条件下的 trader，

#### 1.1.2.2 请求参数

格式：application/json

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                            |
| ------ | ------ | -------- | ------------------------------- |
| time   | String | 不必须   | 时间限制(1W,1M,6M,1Y)           |
| label  | String | 不必须   | 条件限制(小于$1000，总额最高等) |
|        |        |          |                                 |

请求参数样例：

```json
{
	"time": "1M",
    "label":"Under$1000"
}
```



#### 1.1.2.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": [
  	{
     	"traderId":1,
      	"name": "xxx",
        "avatarUrl": "http://",
        "ROI": 22.22%,
      	"copiers": 2
  	}
  ]
}
```

### 1.1.3 收藏 trader

#### 1.1.3.1 基本信息

> 请求路径：/trader (存疑，我没有账号不知道用户的路径长什么样)
>
> 请求方式：POST
>
> 接口描述：该接口用于新增 trader 到用户的 trader 收藏内

#### 1.1.3.2 请求参数

格式：application/json

参数说明：

| 参数名   | 类型 | 是否必须 | 备注         |
| -------- | ---- | -------- | ------------ |
| traderId | Long | 必须     | trader 的 ID |

请求参数样例：

```json
{
  "tradeId": 1
}
```

#### 1.1.3.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```



## 1.2 市场(markets)页面(zulu 的 markets)

### 1.2.1 市场页面主体

## (我觉得应当要添加一个类型的变量，来区分不同的交易市场，否者查找的时候会很麻烦)

#### 1.2.1.1 基本信息

> 请求路径：/markets
>
> 请求方式：GET
>
> 接口描述：该接口用于获得基本的市场信息

#### 1.2.1.2 请求参数

无

#### 1.2.1.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": [
  	"forex":[
		{
      		"id":1,
      		"imstrument": "xxx",
      		"currentPrice": 143.23,
      		"volatility": 0.23%
      	}
	],
	"stocks":[
		{
      		"id":1,
      		"imstrument": "xxx",
      		"currentPrice": 143.23,
      		"volatility": 0.23%
      	}
	],
	"cryptoCFD":[
       {
       		"id":1,
      		"imstrument": "xxx",
      		"currentPrice": 143.23,
      		"volatility": 0.23%
       }
    ],
	"Indices":[
		{
      		"id":1,
      		"imstrument": "xxx",
      		"currentPrice": 143.23,
      		"volatility": 0.23%
      	}
	],
	"Commodities":[
    	{
            "id":1,
      		"imstrument": "xxx",
      		"currentPrice": 143.23,
      		"volatility": 0.23%
		}
    ]
  ]
}
```

### 1.2.2 获取具体的交易的信息

#### 1.2.2.1 基本信息

> 请求路径：/markets
>
> 请求方式：GET
>
> 接口描述：该接口用于获得某项交易的信息

#### 1.2.2.2 请求参数

格式：application/json

参数说明：

| 参数名   | 类型 | 是否必须 | 备注          |
| -------- | ---- | -------- | ------------- |
| marketId | Long | 必须     | 市场数据的 ID |

请求参数样例：

```json
{
  "id": 1
}
```

#### 

#### 1.2.2.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 必须     | 提示信息                       |
| data   | object | 不必须   | 返回的数据                     |
|        |        |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": {
        "id":1,
      	"imstrument": "xxx",
      	"currentPrice": 143.23,
      	"openPrice": 132.23,
      	"highPrice": 165.32,
      	"lowPrice": 122.23,
      	"volatility": 0.23%,
    }
	
}
```

### 1.2.3 获取过去时间段内某交易项的价格

#### 1.2.3.1 基本信息

> 请求路径：/markets
>
> 请求方式：GET
>
> 接口描述：该接口用于获得某项交易某时段的时序变化信息

#### 1.2.3.2 请求参数

格式：application/json

参数说明：

| 参数名   | 类型   | 是否必须 | 备注                    |
| -------- | ------ | -------- | ----------------------- |
| marketId | Long   | 必须     | 市场数据的 ID           |
| time     | String | 必须     | 时间长(1D,7D,1M,1Y,YTD) |

请求参数样例：

```json
{
  "id": 1,
  "time": "1M"
}
```

#### 

#### 1.2.3.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 必须     | 提示信息                       |
| data   | object | 不必须   | 返回的数据                     |
|        |        |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": {
        "marketId":1,
      	"history":"json"
  }
}
```

### 1.2.4 买入卖出

#### 1.2.4.1 基本信息

> 请求路径：/markets
>
> 请求方式：PUT
>
> 接口描述：该接口用于买入卖出持有的资产

#### 1.2.4.2 请求参数

格式：application/json

参数说明：

| 参数名   | 类型   | 是否必须 | 备注          |
| -------- | ------ | -------- | ------------- |
| marketId | Long   | 必须     | 市场数据的 ID |
| type     | String | 必须     | 买入 or 卖出  |
| volume   | Double | 必须     | 交易量        |

请求参数样例：

```json
{
  "marketId": 1,
  "type": "买入",
  "amount": 143.42
}
```



#### 1.2.4.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 必须     | 提示信息                       |
| data   | object | 不必须   | 返回的数据                     |
|        |        |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": {
        "marketId":1,
      	"volume": 23.23
    }
	
}
```

### 1.2.5 买入卖出

#### 1.2.5.1 基本信息

> 请求路径：/markets
>
> 请求方式：POST
>
> 接口描述：该接口用于将交易项加入关注列表

#### 1.2.4.2 请求参数

格式：application/json

参数说明：

| 参数名   | 类型 | 是否必须 | 备注          |
| -------- | ---- | -------- | ------------- |
| marketId | Long | 必须     | 市场数据的 ID |
|          |      |          |               |
|          |      |          |               |

请求参数样例：

```json
{
  "marketId": 1,
}
```



#### 1.2.4.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 必须     | 提示信息                       |
| data   | object | 不必须   | 返回的数据                     |
|        |        |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```



## 1.3 工具(Tool)(zulu 的 tool)

### 1.3.1 货币换算

#### 1.3.1.1 基本信息

> 请求路径：/trading-tools/margin-clalculator
>
> 请求方式：GET
>
> 接口描述：该接口用于货币换算的信息

#### 1.3.1.2 请求参数

格式：application/json

参数说明：

| 参数名     | 类型   | 是否必须 | 备注     |
| ---------- | ------ | -------- | -------- |
| instrument | String | 必须     | 交易类型 |
| volume     | Double | 必须     | 交易量   |
|            |        |          |          |

请求参数样例：

```json
{
	"instrument": "AUD/CAD",
	"volume": 323.23
}
```



#### 1.3.1.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 必须     | 提示信息                       |
| data   | object | 不必须   | 返回的数据                     |
|        |        |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data":{
      "volumes": 423.23
  }
}
```

### 1.3.2 获取博客文章(我觉得没啥用，zulu里的都几年没更新过了)

#### 1.3.2.1 基本信息

> 请求路径：/trading-tools/knowledge-crunch
>
> 请求方式：GET
>
> 接口描述：该接口用于获取知识贴

#### 1.3.2.2 请求参数

无

#### 1.3.2.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data":[
      {
          "articleId": 1,
          "content": "text",
          "timeStamps": "2024-5-12"
      }
  ]
}
```



## 1.4 交流社区(Community)页面(zulu 的 community)

### 1.4.1 获取评论

#### 1.4.1.1 基本信息

> 请求路径：/social-feed
>
> 请求方式：GET
>
> 接口描述：该接口用于加载某个类型的已有评论外的前10条社区评论

#### 1.4.1.2 请求参数

格式：application/json

参数说明：

| 参数名     | 类型   | 是否必须 | 备注           |
| ---------- | ------ | -------- | -------------- |
| label      | String | 不必须   | 类型           |
| commentNum | Intger | 必须     | 已加载的评论数 |
|            |        |          |                |

请求参数样例：

```json
{
	"label":"#toptraders"，
    "commentNum": 0
}
```



#### 1.4.1.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data":[
      {
          "commentId": 1,
          "label": ["#toptraders","#marketnews"],
          "traderId": 23,
          "commentNum": 2,
          "likeNum": 23,
          "content": "text",
          "timeStamps": "2024-5-12"
      }
  ]
}
```

### 1.4.2 点赞

#### 1.4.2.1 基本信息

> 请求路径：/social-feed
>
> 请求方式：POST
>
> 接口描述：该接口用于加载前10条社区评论

#### 1.4.2.2 请求参数

格式：application/json

参数说明：

| 参数名    | 类型 | 是否必须 | 备注   |
| --------- | ---- | -------- | ------ |
| commentId | Long | 必须     | 评论号 |
|           |      |          |        |
|           |      |          |        |

请求参数样例：

```json
{
	"commentId": 3,
}
```



#### 1.4.2.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 必须     | 提示信息                       |
| data   | object | 不必须   | 返回的数据                     |
|        |        |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data":{
      "commentId": 1,
      "likeNum": 3
  }
}
```

### 1.4.3 展开评论的回复

#### 1.4.3.1 基本信息

> 请求路径：/social-feed
>
> 请求方式：GET
>
> 接口描述：该接口用于展开评论的回复

#### 1.4.3.2 请求参数

格式：application/json

参数说明：

| 参数名    | 类型 | 是否必须 | 备注   |
| --------- | ---- | -------- | ------ |
| commentId | Long | 必须     | 评论号 |
|           |      |          |        |
|           |      |          |        |

请求参数样例：

```json
{
	"commentId": 3,
}
```



#### 1.4.3.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data":[
      {
          "commentId": 3,
          "traderId": 23,
          "followerId":4,
          "likeNum": 23,
          "content": "text",
          "timeStamps": "2024-5-12"
      }
  ]
}
```

### 1.4.4 回复评论功能

#### 1.4.4.1 基本信息

> 请求路径：/social-feed
>
> 请求方式：POST
>
> 接口描述：该接口用于回复评论

#### 1.4.4.2 请求参数

格式：application/json

参数说明：

| 参数名    | 类型   | 是否必须 | 备注             |
| --------- | ------ | -------- | ---------------- |
| commentId | Long   | 必须     | 回复对象的评论号 |
| content   | String | 必须     | 回复内容         |
|           |        |          |                  |

请求参数样例：

```json
{
	"commentId": 3,
    "content":"text"
}
```



#### 1.4.4.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data":[
      {
          "commentId": 3,
          "traderId": 23,
          "followerId":4,
          "likeNum": 23,
          "content": "text",
          "timeStamps": "2024-5-12"
      }
  ]
}
```

### 1.4.5 删除评论功能

#### 1.4.5.1 基本信息

> 请求路径：/social-feed
>
> 请求方式：DELETE
>
> 接口描述：该接口用于删除评论

#### 1.4.5.2 请求参数

格式：application/json

参数说明：

| 参数名    | 类型 | 是否必须 | 备注             |
| --------- | ---- | -------- | ---------------- |
| commentId | Long | 必须     | 删除对象的评论号 |
|           |      |          |                  |
|           |      |          |                  |

请求参数样例：

```json
{
	"commentId": 3,
}
```



#### 1.4.5.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

### 1.4.6 取消点赞

#### 1.4.6.1 基本信息

> 请求路径：/social-feed
>
> 请求方式：PUT
>
> 接口描述：该接口用于取消点赞

#### 1.4.6.2 请求参数

格式：application/json

参数说明：

| 参数名    | 类型 | 是否必须 | 备注         |
| --------- | ---- | -------- | ------------ |
| commentId | Long | 必须     | 对象的评论号 |
|           |      |          |              |
|           |      |          |              |

请求参数样例：

```json
{
	"commentId": 3,
}
```



#### 1.4.6.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": {
      "commentId": 4,
      "likeNum": 23,
  }
}
```

### 

## 1.5 关于网页(About)弹框栏(zulu 的 About)

## (没有后端交互)



## 1.6 帮助中心(Help Center)页面(zulu 的 help center)

## (没有后端交互)



## 1.7 首页(zulu 的 ZuluTrade)

## (没有后端交互)



## 1.8 交易员检索页面(点击 Leaders 中任意 View All 跳转到的页面)

## (对这个和trader页面的View All进行了集成，现在它们通过 请求参数 综合控制，而非请求改变路径。这样更简便)



## 1.9 模拟(Simulation)页面(点击 Leaders 中的 Try Now)

### 1.9.1 添加模拟资金

#### 1.9.1.1 基本信息

> 请求路径：/simulation
>
> 请求方式：PUT
>
> 接口描述：该接口用于添加模拟资金

#### 1.9.1.2 请求参数

格式：application/json

参数说明：

| 参数名   | 类型   | 是否必须 | 备注            |
| -------- | ------ | -------- | --------------- |
| amount   | Double | 必须     | 模拟资金量      |
| time     | Intger | 必须     | 时间范围(秒)    |
| traderId | Long[] | 必须     | 模拟跟随traders |

请求参数样例：

```json
{
	"amount": 10000,
    "time": 600,
    "traderId": [1,3,4]
}
```



#### 1.9.1.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型     | 是否必须 | 备注                           |
| ------ | -------- | -------- | ------------------------------ |
| code   | number   | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string   | 必须     | 提示信息                       |
| data   | object[] | 不必须   | 返回的数据                     |
|        |          |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

### 

## 2 登录相关页面

## 2.1 登录(login)页面(zulu 的 login)

### 2.1.1 登录

#### 2.1.1.1 基本信息

> 请求路径：/login
>
> 请求方式：POST
>
> 接口描述：该接口用于登录

#### 2.1.1.2 请求参数

格式：x-www-form-urlencoded

参数说明：

| 参数名   | 类型   | 是否必须 | 备注           |
| -------- | ------ | -------- | -------------- |
| username | String | 必须     | 5-16为非空字符 |
| password | String | 必须     | 5-16为非空字符 |
|          |        |          |                |

请求参数样例：

```json
username=xxx&password=123324
```



#### 2.1.1.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 必须     | 提示信息                       |
| data   | String | 必须     | 返回的数据，jwt令牌            |
|        |        |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": "sdfwe23fewrweerfrgefsdreferszxf.eyJgasdrafOniagdaKnaisngnsdngas4bib23bibibsadfdsffbvisaddfsrasn8nnini3bhilh.pE_RAgdfafdagadfsdanijeilmfadsini235M"
}
```

### 

## 2.2 注册(sign up)页面(zulu 的 sign up)

### 2.2.1 注册

#### 2.2.1.1 基本信息

> 请求路径：/register/
>
> 请求方式：POST
>
> 接口描述：该接口用于注册用户

#### 2.2.1.2 请求参数

格式：x-www-form-urlencoded

参数说明：

| 参数名   | 类型   | 是否必须 | 备注           |
| -------- | ------ | -------- | -------------- |
| username | String | 必须     | 5-16为非空字符 |
| password | String | 必须     | 5-16为非空字符 |
|          |        |          |                |

请求参数样例：

```json
username=xxx&password=123324
```



#### 2.2.1.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 必须     | 提示信息                       |
| data   | String | 必须     | 返回的数据                     |
|        |        |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```

### 

## 2.3 忘记密码(Forgot Password)页面

### 2.3.1 更新密码

#### 2.3.1.1 基本信息

> 请求路径：/register/
>
> 请求方式：PATCH
>
> 接口描述：该接口用于跟新密码

#### 2.3.1.2 请求参数

格式：application/json

参数说明：

| 参数名  | 类型   | 是否必须 | 备注           |
| ------- | ------ | -------- | -------------- |
| old_pwd | String | 必须     | 5-16为非空字符 |
| new_pwd | String | 必须     | 5-16为非空字符 |
| re_pwd  | String | 必须     |                |

请求参数样例：

```json
{
    "old_pwd": "12345",
    "new_pwd": "23456",
    "re_pwd": "23456"
}
```



#### 2.3.1.3 响应数据

参数格式：application/json

参数说明：

| 参数名 | 类型   | 是否必须 | 备注                           |
| ------ | ------ | -------- | ------------------------------ |
| code   | number | 必须     | 响应码，1 代表成功，0 代表失败 |
| msg    | string | 必须     | 提示信息                       |
| data   | String | 必须     | 返回的数据                     |
|        |        |          |                                |

响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": null
}
```





## 3 跟投者相关页面

## 3.1 跟投者主页(dashboard)

	---

	#### 3.1.1 获取账户总览信息

	- **请求路径**：`/dashboard/overview`  
	- **请求方式**：`GET`  
	- **接口描述**：获取跟投者账户的基本信息，包括总资产价值、总盈亏、复制状态等。  

	##### 请求参数：

	无  

	##### 响应数据：  

	| 参数名               | 类型     | 是否必须 | 备注                                    |
	|---------------------|----------|----------|-----------------------------------------|
	| `code`              | `number` | 必须     | 响应码，1 代表成功，0 代表失败          |
	| `msg`               | `string` | 必须     | 提示信息                                |
	| `totalPortfolioValue` | `number` | 必须     | 账户总资产（美元）                      |
	| `totalPnL`          | `number` | 必须     | 总收益或亏损                            |
	| `copying`           | `number` | 必须     | 当前复制的交易员数                      |
	| `following`         | `number` | 必须     | 当前关注的交易员数                      |

	##### 响应数据样例：

	```json
	{
	"code": 1,
	"msg": "success",
	"totalPortfolioValue": 10000.00,
	"totalPnL": 200.00,
	"copying": 2,
	"following": 5
	}
	```

	---

	#### 3.1.2 获取资产变化历史数据

	- **请求路径**：`/dashboard/equity-history`  
	- **请求方式**：`GET`  
	- **接口描述**：获取跟投者账户的资产变化历史，用于绘制“Equity in $”图表。  

	##### 请求参数：

	| 参数名   | 类型     | 是否必须 | 备注                                 |
	|----------|----------|----------|-------------------------------------|
	| `period` | `string` | 必须     | 数据时间范围，如 `7D`（7天）、`1M`（1个月）、`1Y`（1年）、`All`（所有）|

	##### 响应数据：

	| 参数名    | 类型     | 是否必须 | 备注                           |
	|-----------|----------|----------|-------------------------------|
	| `code`    | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
	| `msg`     | `string` | 必须     | 提示信息                       |
	| `data`    | `object[]` | 必须   | 返回的资产变化历史数据         |
	| `data[].timestamp` | `string` | 必须 | 时间戳，格式如 `2024-08-31T12:00:00Z` |
	| `data[].equity`    | `number` | 必须 | 相应时间点的账户资产（美元）   |

	##### 响应数据样例：

	```json
	{
	"code": 1,
	"msg": "success",
	"data": [
		{
		"timestamp": "2024-08-31T12:00:00Z",
		"equity": 10000.00
		},
		{
		"timestamp": "2024-09-01T12:00:00Z",
		"equity": 10100.00
		}
	]
	}
	```

	---

	#### 3.1.3 获取交易历史

	- **请求路径**：`/dashboard/trades`  
	- **请求方式**：`GET`  
	- **接口描述**：获取跟投者账户的交易历史，包括已开仓、挂单和历史记录。  

	##### 请求参数：

	| 参数名   | 类型     | 是否必须 | 备注                          |
	|----------|----------|----------|------------------------------|
	| `status` | `string` | 必须     | `Open`、`Pending`、`History` 用以筛选不同状态的交易 |

	##### 响应数据：

	| 参数名          | 类型     | 是否必须 | 备注                                    |
	|-----------------|----------|----------|-----------------------------------------|
	| `code`          | `number` | 必须     | 响应码，1 代表成功，0 代表失败          |
	| `msg`           | `string` | 必须     | 提示信息                                |
	| `data`          | `object[]` | 必须   | 返回的交易数据                          |
	| `data[].tradeId` | `number` | 必须     | 交易唯一标识符                          |
	| `data[].instrument` | `string` | 必须 | 交易的金融工具（如股票、外汇对）         |
	| `data[].openTime`   | `string` | 可选   | 开仓时间，格式如 `2024-08-31T12:00:00Z` |
	| `data[].closeTime`  | `string` | 可选   | 平仓时间（如适用），格式如 `2024-09-01T12:00:00Z` |
	| `data[].volume`     | `number` | 必须   | 交易量                                  |
	| `data[].profitLoss` | `number` | 必须   | 利润或亏损金额                          |
	| `data[].status`     | `string` | 必须   | 交易状态                                |

	##### 响应数据样例：

	```json
	{
	"code": 1,
	"msg": "success",
	"data": [
		{
		"tradeId": 101,
		"instrument": "EUR/USD",
		"openTime": "2024-08-31T12:00:00Z",
		"closeTime": null,
		"volume": 1.5,
		"profitLoss": 150.00,
		"status": "Open"
		}
	]
	}
	```

	---

	#### 3.1.4 添加或删除复制交易员

	- **请求路径**：`/dashboard/copy-trader`  
	- **请求方式**：`POST / DELETE`  
	- **接口描述**：用于添加或移除复制的交易员。  

	##### 请求参数：

	| 参数名   | 类型     | 是否必须 | 备注                        |
	|----------|----------|----------|----------------------------|
	| `traderId` | `number` | 必须     | 交易员的唯一标识符          |
	| `action` | `string` | 必须     | 操作类型，`add` 或 `remove` |

	##### 响应数据：

	| 参数名 | 类型     | 是否必须 | 备注                           |
	|--------|----------|----------|--------------------------------|
	| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
	| `msg`  | `string` | 必须     | 提示信息                       |

	##### 响应数据样例：

	```json
	{
	"code": 1,
	"msg": "Trader added successfully."
	}
	```

	---

	#### 3.1.5 更新跟投者账户设置

	- **请求路径**：`/dashboard/settings`  
	- **请求方式**：`PUT`  
	- **接口描述**：用于更新跟投者账户的设置，如风险管理参数、通知设置等。  

	##### 请求参数：

	| 参数名        | 类型     | 是否必须 | 备注                                    |
	|---------------|----------|----------|----------------------------------------|
	| `riskLevel`   | `string` | 必须     | 风险等级                                |
	| `notifications` | `object` | 可选     | 通知设置，包含 `email`、`SMS` 等字段   |

	##### 响应数据：

	| 参数名 | 类型     | 是否必须 | 备注                           |
	|--------|----------|----------|--------------------------------|
	| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
	| `msg`  | `string` | 必须     | 提示信息                       |

	##### 响应数据样例：

	```json
	{
	"code": 1,
	"msg": "Settings updated successfully."
	}
	```

	---

	

## 3.2 个人信息(personal detail)
### 修改后的通用接口设计

		为了使得该部分的 API 文档适用于 `Follower` 和 `Trader`，可以在接口路径和数据结构设计上做一些通用化的调整。这里的关键点在于确保接口能够兼容 `Follower` 和 `Trader` 两种用户类型，并能够根据用户的类型（`type`）来执行不同的业务逻辑。



		1. **通用化请求路径**：将 `/dashboard/personal-info` 修改为 `/user/{type}/personal-info`，其中 `{type}` 可以是 `follower` 或 `trader`。这样接口路径可以适应两种用户类型。


		### 通用接口设计

		#### 3.2.1 获取个人信息

		- **请求路径**：`/user/{type}/personal-info`  
		- **请求方式**：`GET`  
		- **接口描述**：获取当前登录用户（`Follower` 或 `Trader`）的个人信息，包括系统自动生成的头像信息。

		##### 请求参数：

		- `{type}`: `string` - 用户类型，`follower` 或 `trader`

		##### 响应数据：

		| 参数名                | 类型      | 是否必须 | 备注                                                      |
		|----------------------|-----------|----------|-----------------------------------------------------------|
		| `code`               | `number`  | 必须     | 响应码，1 代表成功，0 代表失败                            |
		| `msg`                | `string`  | 必须     | 提示信息                                                  |
		| `data`               | `object`  | 必须     | 返回的用户个人信息                                        |
		| `data.name`          | `string`  | 必须     | 用户姓名                                                  |
		| `data.surname`       | `string`  | 必须     | 用户姓氏                                                  |
		| `data.country`       | `string`  | 必须     | 用户所在国家                                              |
		| `data.email`         | `string`  | 必须     | 用户邮箱地址                                              |
		| `data.phone`         | `string`  | 可选     | 用户手机号码                                              |
		| `data.phoneVerified` | `boolean` | 必须     | 手机号码是否已验证                                        |
		| `data.displayName`   | `string`  | 可选     | 显示名称（可选项）                                        |
		| `data.generatedAvatar` | `string` | 必须     | 自动生成的头像 URL，基于姓名或姓氏的首字母与背景颜色       |
		| `data.verifiedStatus`| `boolean` | 必须     | 邮箱或手机验证状态                                        |
		| `data.traderSpecificField` | `string` | 可选  | 如果用户是 `Trader`，可能有的特定字段（否则为 `null`）     |
		| `data.followerSpecificField` | `string` | 可选  | 如果用户是 `Follower`，可能有的特定字段（否则为 `null`）   |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "success",
		"data": {
			"name": "TQ",
			"surname": "X",
			"country": "France",
			"email": "x1341699206@gmail.com",
			"phone": "+33644629442",
			"phoneVerified": false,
			"displayName": "TX",
			"generatedAvatar": "http://example.com/avatar.png",
			"verifiedStatus": true,
			"traderSpecificField": "ExampleTraderField",
			"followerSpecificField": null
		}
		}
		```

		---

		#### 3.2.2 更新个人信息

		- **请求路径**：`/user/{type}/personal-info`  
		- **请求方式**：`PUT`  
		- **接口描述**：用于更新当前登录用户（`Follower` 或 `Trader`）的个人信息。头像部分无需更新，将自动由系统生成。

		##### 请求参数：

		| 参数名           | 类型     | 是否必须 | 备注                                |
		|------------------|----------|----------|-------------------------------------|
		| `{type}`         | `string` | 必须     | 用户类型，`follower` 或 `trader`    |
		| `name`           | `string` | 必须     | 用户姓名                            |
		| `surname`        | `string` | 必须     | 用户姓氏                            |
		| `country`        | `string` | 必须     | 用户所在国家                        |
		| `email`          | `string` | 必须     | 用户邮箱地址                        |
		| `phone`          | `string` | 可选     | 用户手机号码                        |
		| `displayName`    | `string` | 可选     | 显示名称（可选项）                  |

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|-------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "Personal information updated successfully."
		}
		```

		---



## 3.3 交易账户(trading accounts)

		#### 3.3.1 获取所有模拟交易账户信息

		- **请求路径**：`/user/{type}/trading-accounts`  
		- **请求方式**：`GET`  
		- **接口描述**：获取指定用户类型的所有模拟交易账户信息，包括账户状态、类型和余额。  

		##### 请求参数：

		- `{type}`: `string` - 用户类型，`follower` 或 `trader`

		##### 响应数据：

		| 参数名                      | 类型       | 是否必须 | 备注                           |
		|-----------------------------|------------|----------|--------------------------------|
		| `code`                      | `number`   | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`                       | `string`   | 必须     | 提示信息                       |
		| `data`                      | `object[]` | 必须     | 返回的模拟交易账户数据         |
		| `data[].accountId`          | `string`   | 必须     | 模拟交易账户的唯一标识符       |
		| `data[].accountType`        | `string`   | 必须     | 账户类型（固定为 `DEMO`）      |
		| `data[].accountStatus`      | `string`   | 必须     | 账户状态（如 `CONNECTED`）     |
		| `data[].balance`            | `number`   | 必须     | 当前账户余额（虚拟资金，美元） |
		| `data[].currency`           | `string`   | 必须     | 账户货币类型（如 `USD`）       |
		| `data[].isDemo`             | `boolean`  | 必须     | 是否为模拟账户（始终为 `true`）|
		| `data[].createdAt`          | `string`   | 可选     | 账户创建时间，格式如 `2024-08-31T12:00:00Z` |
		| `data[].updatedAt`          | `string`   | 可选     | 账户更新时间，格式如 `2024-08-31T12:00:00Z` |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "success",
		"data": [
			{
			"accountId": "DM2680789",
			"accountType": "DEMO",
			"accountStatus": "CONNECTED",
			"balance": 10000.00,
			"currency": "USD",
			"isDemo": true,
			"createdAt": "2024-08-01T12:00:00Z",
			"updatedAt": "2024-09-15T12:00:00Z"
			}
		]
		}
		```

		---

		#### 3.3.2 添加模拟交易账户

		- **请求路径**：`/user/{type}/trading-accounts`  
		- **请求方式**：`POST`  
		- **接口描述**：用于添加新的模拟交易账户。  

		##### 请求参数：

		| 参数名        | 类型     | 是否必须 | 备注                         |
		|---------------|----------|----------|------------------------------|
		| `{type}`      | `string` | 必须     | 用户类型，`follower` 或 `trader` |
		| `currency`    | `string` | 必须     | 账户货币类型（如 `USD`）     |

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|--------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |
		| `data` | `object` | 可选     | 新添加的模拟账户信息           |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "Demo account added successfully.",
		"data": {
			"accountId": "BBB1234567",
			"accountType": "DEMO",
			"balance": 0.00,
			"currency": "USD",
			"isDemo": true,
			"createdAt": "2024-09-15T12:00:00Z"
		}
		}
		```

		---

		#### 3.3.3 移除模拟交易账户

		- **请求路径**：`/user/{type}/trading-accounts/{accountId}`  
		- **请求方式**：`DELETE`  
		- **接口描述**：用于移除指定的模拟交易账户。  

		##### 请求参数：

		| 参数名        | 类型     | 是否必须 | 备注                         |
		|---------------|----------|----------|------------------------------|
		| `{type}`      | `string` | 必须     | 用户类型，`follower` 或 `trader` |
		| `{accountId}` | `string` | 必须     | 要移除的模拟交易账户的唯一标识符 |

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|--------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "Demo account removed successfully."
		}
		```

		---

		### 重要调整
		- **取消存款和提款功能**：由于所有账户均为模拟账户，因此不需要进行实际的存款和提款操作，这些操作可以由管理员在后台进行管理和分配虚拟资金。
		- **减少字段复杂性**：由于账户类型为固定的模拟账户，相关的字段（如`isDemo`）设置为固定值，不需要前端用户做出选择。




## 3.4 总体风险设置()

		### 更新后的总体风险设置 API 文档

---


		---

		#### 3.4.1 获取总体风险设置

		- **请求路径**：`/risk/global`  
		- **请求方式**：`GET`  
		- **接口描述**：获取跟投者账户的总体风险设置，用于展示在用户界面上。

		##### 请求参数：

		无  

		##### 响应数据：

		| 参数名                     | 类型     | 是否必须 | 备注                                    |
		|----------------------------|----------|----------|-----------------------------------------|
		| `code`                     | `number` | 必须     | 响应码，1 代表成功，0 代表失败          |
		| `msg`                      | `string` | 必须     | 提示信息                                |
		| `data`                     | `object` | 必须     | 返回的总体风险设置数据                  |
		| `data.maxLoss`             | `number` | 必须     | 最大可承受亏损金额（美元），来自 `RiskManagementSettings` 模型的 `maxLoss` 字段 |
		| `data.leverageLimit`       | `number` | 必须     | 最大杠杆限制，来自 `RiskManagementSettings` 模型的 `leverageLimit` 字段 |
		| `data.stopLossPercentage`  | `number` | 必须     | 止损百分比，来自 `RiskManagementSettings` 模型的 `stopLossPercentage` 字段 |
		| `data.capitalProtection`   | `number` | 可选     | 资金保护金额（美元），来自 `RiskManagementSettings` 模型的 `capitalProtection` 字段 |
		| `data.autoAdjustment`      | `boolean`| 必须     | 是否启用自动调整风险设置，来自 `RiskManagementSettings` 模型的 `autoAdjustment` 字段 |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "success",
		"data": {
			"maxLoss": 1000.00,
			"leverageLimit": 2.0,
			"stopLossPercentage": 5.0,
			"capitalProtection": 500.00,
			"autoAdjustment": true
		}
		}
		```

		---

		#### 3.4.2 更新总体风险设置

		- **请求路径**：`/risk/global`  
		- **请求方式**：`PUT`  
		- **接口描述**：更新跟投者账户的总体风险设置。

		##### 请求参数：

		| 参数名                     | 类型     | 是否必须 | 备注                                    |
		|----------------------------|----------|----------|-----------------------------------------|
		| `maxLoss`                  | `number` | 必须     | 最大可承受亏损金额（美元），更新 `RiskManagementSettings` 模型的 `maxLoss` 字段  |
		| `leverageLimit`            | `number` | 必须     | 最大杠杆限制，更新 `RiskManagementSettings` 模型的 `leverageLimit` 字段         |
		| `stopLossPercentage`       | `number` | 必须     | 止损百分比，更新 `RiskManagementSettings` 模型的 `stopLossPercentage` 字段       |
		| `capitalProtection`        | `number` | 可选     | 资金保护金额（美元），更新 `RiskManagementSettings` 模型的 `capitalProtection` 字段 |
		| `autoAdjustment`           | `boolean`| 必须     | 是否启用自动调整风险设置，更新 `RiskManagementSettings` 模型的 `autoAdjustment` 字段 |

		##### 请求数据样例：

		```json
		{
		"maxLoss": 1500.00,
		"leverageLimit": 3.0,
		"stopLossPercentage": 7.0,
		"capitalProtection": 600.00,
		"autoAdjustment": false
		}
		```

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|-------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "Global risk settings updated successfully."
		}
		```

		---

		#### 3.4.3 重置总体风险设置

		- **请求路径**：`/risk/global/reset`  
		- **请求方式**：`POST`  
		- **接口描述**：重置跟投者账户的总体风险设置为默认值。

		##### 请求参数：

		无  

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|-------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "Global risk settings reset to default."
		}
		```

		---

		#### 3.4.4 启用或禁用自动风险调整

		- **请求路径**：`/risk/global/auto-adjust`  
		- **请求方式**：`PATCH`  
		- **接口描述**：启用或禁用自动风险调整功能。

		##### 请求参数：

		| 参数名           | 类型      | 是否必须 | 备注                           |
		|------------------|-----------|----------|-------------------------------|
		| `autoAdjustment` | `boolean` | 必须     | `true` 启用，`false` 禁用      |

		##### 请求数据样例：

		```json
		{
		"autoAdjustment": true
		}
		```

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|-------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "Auto adjustment enabled."
		}
		```

		---




## 3.5 单独风险设置()

		---

		#### 3.5.1 获取单独风险设置

		- **请求路径**：`/risk/individual/{traderId}`
		- **请求方式**：`GET`
		- **接口描述**：获取跟投者对指定交易员的单独风险设置，用于展示在用户界面上。

		##### 请求参数：

		| 参数名     | 类型   | 是否必须 | 备注                        |
		|------------|--------|----------|-----------------------------|
		| `traderId` | `long` | 必须     | 交易员的唯一标识符          |

		##### 响应数据：

		| 参数名                              | 类型     | 是否必须 | 备注                                                                 |
		|-------------------------------------|----------|----------|----------------------------------------------------------------------|
		| `code`                              | `number` | 必须     | 响应码，1 代表成功，0 代表失败                                        |
		| `msg`                               | `string` | 必须     | 提示信息                                                             |
		| `data`                              | `object` | 必须     | 返回的单独风险设置数据                                               |
		| `data.traderId`                     | `long`   | 必须     | 交易员的唯一标识符，来自 `Trader` 模型的 `traderId` 字段             |
		| `data.maxLoss`                      | `number` | 必须     | 针对该交易员的最大可承受亏损金额（美元），来自 `CopyTrading` 模型的 `maxLoss` 字段 |
		| `data.stopLossPercentage`           | `number` | 必须     | 针对该交易员的止损百分比，来自 `CopyTrading` 模型的 `stopLossPercentage` 字段    |
		| `data.capitalAllocated`             | `number` | 必须     | 针对该交易员的分配资金金额（美元），来自 `CopyTrading` 模型的 `capitalAllocated` 字段 |
		| `data.trailingStopEnabled`          | `boolean`| 必须     | 是否启用针对该交易员的移动止损，来自 `CopyTrading` 模型的 `trailingStopEnabled` 字段 |
		| `data.trailingStopDistance`         | `number` | 可选     | 移动止损距离（点数），来自 `CopyTrading` 模型的 `trailingStopDistance` 字段     |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "success",
		"data": {
			"traderId": 101,
			"maxLoss": 500.00,
			"stopLossPercentage": 10.0,
			"capitalAllocated": 1000.00,
			"trailingStopEnabled": true,
			"trailingStopDistance": 20
		}
		}
		```

		---

		#### 3.5.2 更新单独风险设置

		- **请求路径**：`/risk/individual/{traderId}`
		- **请求方式**：`PUT`
		- **接口描述**：更新跟投者对指定交易员的单独风险设置。

		##### 请求参数：

		| 参数名                 | 类型      | 是否必须 | 备注                                                                    |
		|------------------------|-----------|----------|-------------------------------------------------------------------------|
		| `traderId`             | `long`    | 必须     | 交易员的唯一标识符                                                      |
		| `maxLoss`              | `number`  | 必须     | 针对该交易员的最大可承受亏损金额（美元），更新 `CopyTrading` 模型的 `maxLoss` 字段 |
		| `stopLossPercentage`   | `number`  | 必须     | 针对该交易员的止损百分比，更新 `CopyTrading` 模型的 `stopLossPercentage` 字段        |
		| `capitalAllocated`     | `number`  | 必须     | 针对该交易员的分配资金金额（美元），更新 `CopyTrading` 模型的 `capitalAllocated` 字段 |
		| `trailingStopEnabled`  | `boolean` | 必须     | 是否启用针对该交易员的移动止损，更新 `CopyTrading` 模型的 `trailingStopEnabled` 字段  |
		| `trailingStopDistance` | `number`  | 可选     | 移动止损距离（点数），更新 `CopyTrading` 模型的 `trailingStopDistance` 字段         |

		##### 请求数据样例：

		```json
		{
		"maxLoss": 500.00,
		"stopLossPercentage": 12.0,
		"capitalAllocated": 800.00,
		"trailingStopEnabled": false,
		"trailingStopDistance": 30
		}
		```

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|-------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "Individual risk settings updated successfully."
		}
		```

		---

		#### 3.5.3 重置单独风险设置

		- **请求路径**：`/risk/individual/{traderId}/reset`
		- **请求方式**：`POST`
		- **接口描述**：重置跟投者对指定交易员的单独风险设置为默认值。

		##### 请求参数：

		| 参数名     | 类型   | 是否必须 | 备注                        |
		|------------|--------|----------|-----------------------------|
		| `traderId` | `long` | 必须     | 交易员的唯一标识符          |

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|-------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "Individual risk settings reset to default."
		}
		```

		---

		#### 3.5.4 启用或禁用单独交易员的移动止损

		- **请求路径**：`/risk/individual/{traderId}/trailing-stop`
		- **请求方式**：`PATCH`
		- **接口描述**：启用或禁用对单独交易员的移动止损功能。

		##### 请求参数：

		| 参数名                | 类型      | 是否必须 | 备注                           |
		|-----------------------|-----------|----------|-------------------------------|
		| `traderId`            | `long`    | 必须     | 交易员的唯一标识符             |
		| `trailingStopEnabled` | `boolean` | 必须     | `true` 启用，`false` 禁用      |

		##### 请求数据样例：

		```json
		{
		"trailingStopEnabled": true
		}
		```

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|-------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "Trailing stop enabled for the trader."
		}
		```

		---


## 3.6 我的讨论贴(my posts)
(暂时不实现)

## 3.7 交易历史(trade history)

		#### 3.7.1 获取交易历史

		- **请求路径**：`/user/{type}/trades/history`  
		- **请求方式**：`GET`  
		- **接口描述**：获取指定用户类型的所有交易历史，包括已完成的交易记录和取消的订单记录。用户可以根据日期范围、货币对和交易状态筛选交易记录。

		##### 请求参数：

		| 参数名           | 类型     | 是否必须 | 备注                                         |
		|------------------|----------|----------|----------------------------------------------|
		| `{type}`         | `string` | 必须     | 用户类型，`follower` 或 `trader`             |
		| `startDate`      | `string` | 可选     | 起始日期，格式为 `YYYY-MM-DD`                |
		| `endDate`        | `string` | 可选     | 结束日期，格式为 `YYYY-MM-DD`                |
		| `currencyPair`   | `string` | 可选     | 交易的货币对，例如 `EUR/USD`                 |
		| `tradeStatus`    | `string` | 可选     | 交易状态，`All` 表示所有，`Manual` 表示手动   |
		| `profitType`     | `string` | 可选     | 利润类型，`pips` 表示点数，`usd` 表示美元    |
		| `timePeriod`     | `string` | 可选     | 时间范围，`1D`, `7D`, `1M`, `3M`, `6M`, `1Y`, `All` |

		##### 响应数据：

		| 参数名                  | 类型       | 是否必须 | 备注                                    |
		|-------------------------|------------|----------|-----------------------------------------|
		| `code`                  | `number`   | 必须     | 响应码，1 代表成功，0 代表失败          |
		| `msg`                   | `string`   | 必须     | 提示信息                                |
		| `data`                  | `object[]` | 必须     | 返回的交易历史数据                      |
		| `data[].tradeId`        | `number`   | 必须     | 交易唯一标识符，对应 `Trade` 模型的 `tradeId` |
		| `data[].instrument`     | `string`   | 必须     | 交易的金融工具（如 `EUR/USD`），对应 `Trade` 模型的 `instrument` |
		| `data[].openTime`       | `string`   | 可选     | 开仓时间，格式为 `YYYY-MM-DDTHH:mm:ssZ`，对应 `Trade` 模型的 `openTime` |
		| `data[].closeTime`      | `string`   | 可选     | 平仓时间（如适用），格式为 `YYYY-MM-DDTHH:mm:ssZ`，对应 `Trade` 模型的 `closeTime` |
		| `data[].volume`         | `number`   | 必须     | 交易量，对应 `Trade` 模型的 `volume`      |
		| `data[].profitLoss`     | `number`   | 必须     | 利润或亏损金额，对应 `Trade` 模型的 `profitLoss` |
		| `data[].status`         | `string`   | 必须     | 交易状态，对应 `Trade` 模型的 `status`   |
		| `data[].profitInPips`   | `number`   | 可选     | 以点数计算的利润，派生字段               |
		| `data[].profitInUsd`    | `number`   | 可选     | 以美元计算的利润，派生字段               |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "success",
		"data": [
			{
			"tradeId": 101,
			"instrument": "EUR/USD",
			"openTime": "2024-08-31T12:00:00Z",
			"closeTime": "2024-09-01T12:00:00Z",
			"volume": 1.5,
			"profitLoss": 150.00,
			"status": "Completed",
			"profitInPips": 30,
			"profitInUsd": 150.00
			},
			{
			"tradeId": 102,
			"instrument": "GBP/USD",
			"openTime": "2024-09-02T14:00:00Z",
			"closeTime": null,
			"volume": 2.0,
			"profitLoss": -50.00,
			"status": "Open",
			"profitInPips": -10,
			"profitInUsd": -50.00
			}
		]
		}
		```

		---

		#### 3.7.2 取消订单记录

		- **请求路径**：`/user/{type}/trades/cancelled`  
		- **请求方式**：`GET`  
		- **接口描述**：获取指定用户类型的取消订单记录。

		##### 请求参数：

		| 参数名         | 类型     | 是否必须 | 备注                               |
		|----------------|----------|----------|-----------------------------------|
		| `{type}`       | `string` | 必须     | 用户类型，`follower` 或 `trader`   |
		| `startDate`    | `string` | 可选     | 起始日期，格式为 `YYYY-MM-DD`      |
		| `endDate`      | `string` | 可选     | 结束日期，格式为 `YYYY-MM-DD`      |
		| `currencyPair` | `string` | 可选     | 交易的货币对，例如 `EUR/USD`       |

		##### 响应数据：

		| 参数名               | 类型       | 是否必须 | 备注                                     |
		|----------------------|------------|----------|------------------------------------------|
		| `code`               | `number`   | 必须     | 响应码，1 代表成功，0 代表失败           |
		| `msg`                | `string`   | 必须     | 提示信息                                 |
		| `data`               | `object[]` | 必须     | 返回的取消订单记录                       |
		| `data[].tradeId`     | `number`   | 必须     | 取消订单的唯一标识符，对应 `Trade` 模型的 `tradeId` |
		| `data[].instrument`  | `string`   | 必须     | 交易的金融工具（如 `EUR/USD`），对应 `Trade` 模型的 `instrument` |
		| `data[].cancelTime`  | `string`   | 必须     | 取消时间，格式为 `YYYY-MM-DDTHH:mm:ssZ` |
		| `data[].reason`      | `string`   | 可选     | 取消原因                                 |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "success",
		"data": [
			{
			"tradeId": 103,
			"instrument": "EUR/USD",
			"cancelTime": "2024-09-03T10:00:00Z",
			"reason": "User Requested"
			}
		]
		}
		```

		---

		### 数据映射和说明

		- **数据来源**：
		- `Trade` 模型：所有交易相关数据都从 `Trade` 模型中获取，包括 `tradeId`、`instrument`、`openTime`、`closeTime`、`volume`、`profitLoss`、`status` 等。
		- **派生字段**：`profitInPips` 和 `profitInUsd` 为计算字段，根据 `profitLoss` 和其他交易参数派生。

		- **数据映射**：
		- `tradeId` → `Trade.tradeId`
		- `instrument` → `Trade.instrument`
		- `openTime` → `Trade.openTime`
		- `closeTime` → `Trade.closeTime`
		- `volume` → `Trade.volume`
		- `profitLoss` → `Trade.profitLoss`
		- `status` → `Trade.status`



## 3.8 账户设置(account settings)
（可以暂时不设置）

## 3.9 修改密码(change password)

		- **请求路径**：`/user/{type}/change-password`  
		- **请求方式**：`POST`  
		- **接口描述**：允许用户修改其账户密码，确保只有当前已验证的用户可以执行此操作。该接口适用于 `Follower` 和 `Trader` 两种类型的用户。

		##### 请求参数：

		| 参数名           | 类型     | 是否必须 | 备注                                      |
		|------------------|----------|----------|-------------------------------------------|
		| `{type}`         | `string` | 必须     | 用户类型，`follower` 或 `trader`           |
		| `currentPassword`| `string` | 必须     | 当前密码，必须经过客户端的加密处理后传递   |
		| `newPassword`    | `string` | 必须     | 新密码，必须经过客户端的加密处理后传递     |
		| `confirmPassword`| `string` | 必须     | 确认新密码，必须和 `newPassword` 一致       |

		##### 请求数据样例：

		```json
		{
		"currentPassword": "currentPassword123",
		"newPassword": "newSecurePassword456",
		"confirmPassword": "newSecurePassword456"
		}
		```

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|--------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例：

		成功时：

		```json
		{
		"code": 1,
		"msg": "Password changed successfully."
		}
		```

		失败时：

		```json
		{
		"code": 0,
		"msg": "Current password is incorrect."
		}
		```

		---

		### 数据映射和说明

		- **数据来源**：
		- `Follower` 和 `Trader` 共享通用的用户管理逻辑，密码修改接口将根据请求路径的 `{type}` 确定具体用户类型并执行操作。

		- **数据映射**：
		- `currentPassword` 和 `newPassword` 映射到用户账户的 `password` 字段。需要通过后端进行加密校验。

		### 安全性考虑

		1. **加密传输**：所有密码数据必须通过 HTTPS 传输，确保不会在网络上传输明文密码。
		2. **客户端加密**：在发送密码之前，客户端应用程序应进行基本加密操作，增加额外的安全层。
		3. **强密码策略**：建议新密码符合强密码策略，如最小长度、包含大写字母、小写字母、数字和特殊字符。





## 4 交易员相关页面

		## 4.1 交易员主页
			
		交易员主页是一个单独为交易员用户提供的页面，包括获取交易员详细信息、更新信息、管理交易策略、查看交易表现统计数据，以及查看和管理跟随者。以下是针对交易员的具体 API 文档设计：

		---

		#### 4.1.1 获取交易员详细信息

		- **请求路径**：`/trader/dashboard/details`  
		- **请求方式**：`GET`  
		- **接口描述**：获取当前登录的交易员的详细信息，包括基本资料和交易数据。

		##### 请求参数：

		无

		##### 响应数据：

		| 参数名                 | 类型      | 是否必须 | 备注                                    |
		|------------------------|-----------|----------|-----------------------------------------|
		| `code`                 | `number`  | 必须     | 响应码，1 代表成功，0 代表失败          |
		| `msg`                  | `string`  | 必须     | 提示信息                                |
		| `data`                 | `object`  | 必须     | 返回的交易员详细信息                    |
		| `data.traderId`        | `number`  | 必须     | 交易员的唯一标识符，对应 `Trader` 模型的 `traderId` |
		| `data.name`            | `string`  | 必须     | 交易员姓名，对应 `Trader` 模型的 `name` |
		| `data.country`         | `string`  | 必须     | 交易员所在国家，对应 `Trader` 模型的 `country` |
		| `data.email`           | `string`  | 必须     | 交易员邮箱地址，对应 `Trader` 模型的 `email` |
		| `data.phone`           | `string`  | 可选     | 交易员手机号码，对应 `Trader` 模型的 `phone` |
		| `data.verifiedStatus`  | `boolean` | 必须     | 账户验证状态，对应 `Trader` 模型的 `verifiedStatus` |
		| `data.strategyCount`   | `number`  | 必须     | 交易策略数量，对应 `Trader` 模型的策略数量字段 |
		| `data.followersCount`  | `number`  | 必须     | 跟随者数量，对应 `Trader` 模型的 `followers` 字段 |
		| `data.performance`     | `string`  | 可选     | 交易表现的详细信息，通常为JSON格式字符串 |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "success",
		"data": {
			"traderId": 202,
			"name": "John Doe",
			"country": "USA",
			"email": "john.doe@example.com",
			"phone": "+123456789",
			"verifiedStatus": true,
			"strategyCount": 3,
			"followersCount": 150,
			"performance": "{...}"
		}
		}
		```

		---

		#### 4.1.2 更新交易员信息

		- **请求路径**：`/trader/dashboard/update`  
		- **请求方式**：`PUT`  
		- **接口描述**：更新当前登录的交易员的个人信息。

		##### 请求参数：

		| 参数名        | 类型     | 是否必须 | 备注                                |
		|---------------|----------|----------|-------------------------------------|
		| `name`        | `string` | 必须     | 交易员姓名                          |
		| `country`     | `string` | 必须     | 交易员所在国家                      |
		| `email`       | `string` | 必须     | 交易员邮箱地址                      |
		| `phone`       | `string` | 可选     | 交易员手机号码                      |

		##### 响应数据：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|--------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "Trader information updated successfully."
		}
		```

		---

		#### 4.1.3 获取交易策略列表

		- **请求路径**：`/trader/dashboard/strategies`  
		- **请求方式**：`GET`  
		- **接口描述**：获取当前交易员创建的所有交易策略的列表。

		##### 请求参数：

		无

		##### 响应数据：

		| 参数名                | 类型       | 是否必须 | 备注                                    |
		|-----------------------|------------|----------|-----------------------------------------|
		| `code`                | `number`   | 必须     | 响应码，1 代表成功，0 代表失败          |
		| `msg`                 | `string`   | 必须     | 提示信息                                |
		| `data`                | `object[]` | 必须     | 返回的交易策略列表数据                  |
		| `data[].strategyId`   | `number`   | 必须     | 交易策略的唯一标识符，对应 `TraderScripts` 模型的 `scriptId` |
		| `data[].strategyName` | `string`   | 必须     | 交易策略名称，对应 `TraderScripts` 模型的 `scriptName` |
		| `data[].language`     | `string`   | 必须     | 交易策略使用的脚本语言，例如 `Python`   |
		| `data[].createdAt`    | `string`   | 必须     | 策略创建时间，格式为 `YYYY-MM-DDTHH:mm:ssZ` |
		| `data[].isActive`     | `boolean`  | 必须     | 策略是否激活，对应 `TraderScripts` 模型的 `isActive` |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "success",
		"data": [
			{
			"strategyId": 301,
			"strategyName": "Scalping Strategy",
			"language": "Python",
			"createdAt": "2024-08-15T10:00:00Z",
			"isActive": true
			},
			{
			"strategyId": 302,
			"strategyName": "Swing Trading Strategy",
			"language": "Python",
			"createdAt": "2024-09-01T08:30:00Z",
			"isActive": false
			}
		]
		}
		```

		---

		#### 4.1.4 创建/编辑/删除交易策略

		- **请求路径**：  
		- 创建策略：`/trader/dashboard/strategies` (POST)  
		- 编辑策略：`/trader/dashboard/strategies/{strategyId}` (PUT)  
		- 删除策略：`/trader/dashboard/strategies/{strategyId}` (DELETE)  

		- **接口描述**：交易员可创建、编辑或删除其交易策略。

		##### 请求参数（创建和编辑策略时）：

		| 参数名          | 类型     | 是否必须 | 备注                                |
		|-----------------|----------|----------|-------------------------------------|
		| `strategyName`  | `string` | 必须     | 交易策略名称                        |
		| `language`      | `string` | 必须     | 脚本语言，固定为 `Python`            |
		| `scriptContent` | `string` | 必须     | 交易策略脚本内容，最大限制 10,000 字符 |
		| `description`   | `string` | 可选     | 策略的描述信息                      |
		| `isActive`      | `boolean`| 必须     | 是否激活此策略                       |

		##### 响应数据（创建、编辑和删除）：

		| 参数名 | 类型     | 是否必须 | 备注                           |
		|--------|----------|----------|--------------------------------|
		| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败 |
		| `msg`  | `string` | 必须     | 提示信息                       |

		##### 响应数据样例（创建策略成功）：

		```json
		{
		"code": 1,
		"msg": "Strategy created successfully."
		}
		```

		---

		### 4.1.5 获取交易表现统计数据

		- **请求路径**：`/trader/dashboard/performance`  
		- **请求方式**：`GET`  
		- **接口描述**：获取交易员的交易表现统计数据，用于展示历史绩效指标，如平均收益、平均亏损、最大回撤、总交易数、胜率等。  

		##### 请求参数：

		无

		##### 响应数据：

		| 参数名                   | 类型      | 是否必须 | 备注                                       |
		|--------------------------|-----------|----------|--------------------------------------------|
		| `code`                   | `number`  | 必须     | 响应码，1 代表成功，0 代表失败             |
		| `msg`                    | `string`  | 必须     | 提示信息                                   |
		| `data`                   | `object`  | 必须     | 返回的交易表现统计数据                     |
		| `data.averageProfit`     | `number`  | 必须     | 平均收益，单位为美元                       |
		| `data.averageLoss`       | `number`  | 必须     | 平均亏损，单位为美元                       |
		| `data.maximumDrawdown`   | `number`  | 必须     | 最大回撤，单位为百分比                     |
		| `data.totalTrades`       | `number`  | 必须     | 总交易数                                   |
		| `data.winRate`           | `number`  | 必须     | 胜率，单位为百分比                         |
		| `data.sharpeRatio`       | `number`  | 可选     | 夏普比率，用于衡量风险调整后的收益          |
		| `data.profitFactor`      | `number`  | 可选     | 盈利因子，用于衡量盈利能力                  |

		##### 响应数据样例：

		```json
		{
		"code": 1,
		"msg": "success",
		"data": {
			"averageProfit": 250.50,
			"averageLoss": -150.30,
			"maximumDrawdown": 12.5,
			"totalTrades": 300,
			"winRate": 65.0,
			"sharpeRatio": 1.5,
			"profitFactor": 1.8
		}
		}
		```







明白了，非常感谢你的澄清！在这种情况下，普通管理员和超级管理员在除管理员管理外的其他内容管理上具有相同的权限。唯一的区别在于，超级管理员可以更改普通管理员的所有信息和权限。

基于这一点，我将更新API文档，确保只有超级管理员可以更改其他管理员的相关权限。

### 5.1 管理员主页

#### 5.1.1 获取管理员信息

- **请求路径**: `/api/admin/home`  
- **请求方式**: `GET`  
- **接口描述**: 获取当前登录管理员的详细信息。

##### 请求参数：

| 参数名          | 类型     | 是否必须 | 备注                                |
|-----------------|----------|----------|-------------------------------------|
| `Authorization` | `string` | 必须     | 管理员身份验证令牌（Bearer Token）  |

##### 响应数据：

| 参数名          | 类型     | 是否必须 | 备注                                |
|-----------------|----------|----------|-------------------------------------|
| `adminId`       | `integer`| 必须     | 管理员ID                            |
| `username`      | `string` | 必须     | 管理员用户名                        |
| `email`         | `string` | 必须     | 管理员邮箱                          |
| `role`          | `string` | 必须     | 管理员角色 (`SUPER_ADMIN` or `NORMAL_ADMIN`) |
| `isActive`      | `boolean`| 必须     | 管理员是否激活状态                  |
| `lastLogin`     | `string` | 必须     | 上次登录时间（ISO 8601格式）         |
| `createdAt`     | `string` | 必须     | 管理员创建时间（ISO 8601格式）       |

##### 响应数据样例：

```json
{
  "adminId": 1,
  "username": "adminUser",
  "email": "admin@example.com",
  "role": "SUPER_ADMIN",
  "isActive": true,
  "lastLogin": "2024-09-16T14:48:00.000Z",
  "createdAt": "2023-08-01T10:15:30.000Z"
}
```

---

#### 5.1.2 更新管理员信息

- **请求路径**: `/api/admin/home`  
- **请求方式**: `PUT`  
- **接口描述**: 更新当前登录管理员的详细信息。

##### 权限要求：

- **普通管理员**: 只能更新自己的用户名、邮箱和密码。
- **超级管理员**: 可以更新所有管理员的信息，包括激活状态和角色。

##### 请求参数：

| 参数名      | 类型     | 是否必须 | 备注                                 |
|-------------|----------|----------|--------------------------------------|
| `username`  | `string` | 可选     | 管理员用户名                         |
| `email`     | `string` | 可选     | 管理员邮箱                           |
| `password`  | `string` | 可选     | 管理员密码（应加密）                 |
| `isActive`  | `boolean`| 可选     | 是否激活管理员（**仅限超级管理员**）  |
| `role`      | `string` | 可选     | 管理员角色 (`SUPER_ADMIN` or `NORMAL_ADMIN`, **仅限超级管理员**) |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Admin information updated successfully."
}
```

#### 5.1.3 更新其他管理员信息

- **请求路径**: `/api/admin/manage/{adminId}`  
- **请求方式**: `PUT`  
- **接口描述**: 超级管理员更新其他管理员的详细信息。

##### 权限要求：

- **超级管理员**: 仅超级管理员可以使用此操作。

##### 请求参数：

| 参数名      | 类型     | 是否必须 | 备注                                 |
|-------------|----------|----------|--------------------------------------|
| `adminId`   | `integer`| 必须     | 要更新的管理员ID                     |
| `username`  | `string` | 可选     | 管理员用户名                         |
| `email`     | `string` | 可选     | 管理员邮箱                           |
| `password`  | `string` | 可选     | 管理员密码（应加密）                 |
| `isActive`  | `boolean`| 可选     | 是否激活管理员                        |
| `role`      | `string` | 可选     | 管理员角色 (`SUPER_ADMIN` or `NORMAL_ADMIN`) |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Admin information updated successfully by Super Admin."
}
```

#### 5.1.4 删除其他管理员

- **请求路径**: `/api/admin/manage/{adminId}`  
- **请求方式**: `DELETE`  
- **接口描述**: 超级管理员删除其他管理员。

##### 权限要求：

- **超级管理员**: 仅超级管理员可以删除其他管理员。

##### 请求参数：

| 参数名      | 类型      | 是否必须 | 备注                     |
|-------------|-----------|----------|--------------------------|
| `adminId`   | `integer` | 必须     | 要删除的管理员ID          |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Admin deleted successfully by Super Admin."
}
```

---

### 5.2 交易员账户管理页面

#### 5.2.1 获取所有交易员账户

- **请求路径**: `/api/admin/traders`  
- **请求方式**: `GET`  
- **接口描述**: 获取平台上所有交易员账户的列表，支持分页和排序。

（保持不变）

#### 5.2.2 更新交易员账户

- **请求路径**: `/api/admin/traders/{traderId}`  
- **请求方式**: `PUT`  
- **接口描述**: 更新指定交易员账户的信息。

##### 权限要求：

- **普通管理员和超级管理员**: 可以更新交易员的所有信息。

##### 请求参数：

| 参数名        | 类型      | 是否必须 | 备注                                |
|---------------|-----------|----------|-------------------------------------|
| `traderId`    | `integer` | 必须     | 交易员ID，作为路径参数              |
| `username`    | `string`  | 可选     | 交易员用户名                        |
| `email`       | `string`  | 可选     | 交易员邮箱                          |
| `isActive`    | `boolean` | 可选     | 是否激活该交易员                    |
| `balance`     | `number`  | 可选     | 交易员虚拟账户余额                  |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Trader account updated successfully."
}
```

#### 5.2.3 删除交易员账户

- **请求路径**: `/api/admin/traders/{traderId}`  
- **请求方式**: `DELETE`  
- **接口描述**: 删除指定交易员账户。

##### 权限要求：

- **普通管理员和超级管理员**: 都可以删除交易员账户。

##### 请求参数：

| 参数名     | 类型      | 是否必须 | 备注                     |
|------------|-----------|----------|--------------------------|
| `traderId` | `integer` | 必须     | 交易员ID，作为路径参数    |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1

,
  "msg": "Trader account deleted successfully."
}
```

---

### 5.3 跟投者账户管理页面

#### 5.3.1 获取所有跟投者账户

（保持不变）

#### 5.3.2 更新跟投者账户

- **请求路径**: `/api/admin/followers/{followerId}`  
- **请求方式**: `PUT`  
- **接口描述**: 更新指定跟投者账户的信息。

##### 权限要求：

- **普通管理员和超级管理员**: 可以更新跟投者的所有信息。

##### 请求参数：

| 参数名        | 类型      | 是否必须 | 备注                                |
|---------------|-----------|----------|-------------------------------------|
| `followerId`  | `integer` | 必须     | 跟投者ID，作为路径参数              |
| `username`    | `string`  | 可选     | 跟投者用户名                        |
| `email`       | `string`  | 可选     | 跟投者邮箱                          |
| `isActive`    | `boolean` | 可选     | 是否激活该跟投者                    |
| `balance`     | `number`  | 可选     | 跟投者虚拟账户余额                  |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Follower account updated successfully."
}
```

#### 5.3.3 删除跟投者账户

- **请求路径**: `/api/admin/followers/{followerId}`  
- **请求方式**: `DELETE`  
- **接口描述**: 删除指定跟投者账户。

##### 权限要求：

- **普通管理员和超级管理员**: 都可以删除跟投者账户。

##### 请求参数：

| 参数名      | 类型      | 是否必须 | 备注                     |
|-------------|-----------|----------|--------------------------|
| `followerId`| `integer` | 必须     | 跟投者ID，作为路径参数    |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Follower account deleted successfully."
}
```

---

### 5.4 平台内容管理页面

#### 5.4.1 获取所有内容列表

（保持不变）

#### 5.4.2 创建新内容

- **请求路径**: `/api/admin/content`  
- **请求方式**: `POST`  
- **接口描述**: 创建新的平台内容（如公告、新闻等）。

##### 权限要求：

- **普通管理员和超级管理员**: 都可以创建内容。

##### 请求参数：

| 参数名      | 类型     | 是否必须 | 备注                    |
|-------------|----------|----------|-------------------------|
| `title`     | `string` | 必须     | 内容标题                |
| `body`      | `string` | 必须     | 内容主体                |
| `createdAt` | `string` | 可选     | 内容创建时间，ISO 8601格式 |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Content created successfully."
}
```

#### 5.4.3 更新内容

- **请求路径**: `/api/admin/content/{contentId}`  
- **请求方式**: `PUT`  
- **接口描述**: 更新指定内容。

##### 权限要求：

- **普通管理员和超级管理员**: 都可以更新内容。

##### 请求参数：

| 参数名      | 类型      | 是否必须 | 备注                             |
|-------------|-----------|----------|----------------------------------|
| `contentId` | `integer` | 必须     | 内容ID，作为路径参数              |
| `title`     | `string`  | 可选     | 内容标题                         |
| `body`      | `string`  | 可选     | 内容主体                         |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Content updated successfully."
}
```

#### 5.4.4 删除内容

- **请求路径**: `/api/admin/content/{contentId}`  
- **请求方式**: `DELETE`  
- **接口描述**: 删除指定内容。

##### 权限要求：

- **普通管理员和超级管理员**: 都可以删除内容。

##### 请求参数：

| 参数名      | 类型      | 是否必须 | 备注                     |
|-------------|-----------|----------|--------------------------|
| `contentId` | `integer` | 必须     | 内容ID，作为路径参数      |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Content deleted successfully."
}
```

---

### 5.5 支付和佣金管理页面

#### 5.5.1 获取支付和佣金记录

- **请求路径**: `/api/admin/payments`  
- **请求方式**: `GET`  
- **接口描述**: 获取所有支付和佣金记录，支持分页和排序。

##### 请求参数：

| 参数名    | 类型     | 是否必须 | 备注                        |
|-----------|----------|----------|-----------------------------|
| `page`    | `integer`| 可选     | 页码（默认0）                |
| `size`    | `integer`| 可选     | 每页显示的记录数（默认10）   |
| `sort`    | `string` | 可选     | 排序字段和顺序（如`date,asc`） |

##### 响应数据：

| 参数名                | 类型     | 是否必须 | 备注                            |
|-----------------------|----------|----------|---------------------------------|
| `code`                | `number` | 必须     | 响应码，1 代表成功，0 代表失败   |
| `msg`                 | `string` | 必须     | 提示信息                         |
| `data`                | `object` | 必须     | 返回的支付和佣金数据             |
| `data.payments`       | `array`  | 必须     | 支付和佣金记录列表               |
| `data.totalPages`     | `integer`| 必须     | 总页数                            |
| `data.totalElements`  | `integer`| 必须     | 总记录数                          |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "success",
  "data": {
    "payments": [
      {
        "paymentId": 401,
        "amount": 100.00,
        "currency": "USD",
        "date": "2024-09-10T12:00:00.000Z",
        "status": "completed",
        "commission": 5.00
      }
    ],
    "totalPages": 4,
    "totalElements": 40
  }
}
```

#### 5.5.2 更新支付和佣金记录

- **请求路径**: `/api/admin/payments/{paymentId}`  
- **请求方式**: `PUT`  
- **接口描述**: 更新指定支付和佣金记录。

##### 权限要求：

- **普通管理员和超级管理员**: 都可以更新支付和佣金记录。

##### 请求参数：

| 参数名        | 类型      | 是否必须 | 备注                             |
|---------------|-----------|----------|----------------------------------|
| `paymentId`   | `integer` | 必须     | 支付和佣金记录ID，作为路径参数    |
| `amount`      | `number`  | 可选     | 支付金额                         |
| `currency`    | `string`  | 可选     | 支付货币类型

                      |
| `status`      | `string`  | 可选     | 支付状态（如 `completed`、`pending`） |
| `commission`  | `number`  | 可选     | 佣金金额                         |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Payment and commission record updated successfully."
}
```

#### 5.5.3 删除支付和佣金记录

- **请求路径**: `/api/admin/payments/{paymentId}`  
- **请求方式**: `DELETE`  
- **接口描述**: 删除指定支付和佣金记录。

##### 权限要求：

- **普通管理员和超级管理员**: 都可以删除支付和佣金记录。

##### 请求参数：

| 参数名      | 类型      | 是否必须 | 备注                     |
|-------------|-----------|----------|--------------------------|
| `paymentId` | `integer` | 必须     | 支付和佣金记录ID，作为路径参数 |

##### 响应数据：

| 参数名 | 类型     | 是否必须 | 备注                           |
|--------|----------|----------|--------------------------------|
| `code` | `number` | 必须     | 响应码，1 代表成功，0 代表失败  |
| `msg`  | `string` | 必须     | 提示信息                        |

##### 响应数据样例：

```json
{
  "code": 1,
  "msg": "Payment and commission record deleted successfully."
}
```

