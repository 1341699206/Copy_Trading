# Copying trade接口文档-V1.0

## 1. 页面管理

### 1.1.1 Trader页面(zulu的leader页面)

#### 1.1.1.1 基本信息

> 请求路径：/trader
>
> 请求方式：GET
>
> 接口描述：该接口用于获取 收益率为正，年最佳表现前5，小额收益前5，复制金额前5，2年内收益率为正，最近1月收益为正的各项的trader的降序。



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

响应数据样例：(avatarUrl是头像的存储地址)

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



### 1.1.2 查询总收益率为正(降序)的

#### 1.1.2.1 基本信息

> 请求路径：/trader/list/00001
>
> 请求方式：GET
>
> 接口描述：该接口用于查询总收益率为正的trader，

#### 1.1.2.2 请求参数

无



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



### 1.1.3 查询最近一年收益率为正(降序)的

#### 1.1.3.1 基本信息

> 请求路径：/trader/list/00002
>
> 请求方式：GET
>
> 接口描述：该接口用于获取最近一年内收益率为正的trader,

#### 1.1.3.2 请求参数

无



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
  "data": [
  	{
     	"traderId":1,
      	"name": "xxx",
        "avatarUrl": "http://",
        "ROI": 22.22%,
        "copiers":22
  	}
  ]
}
```



### 1.1.4 查询低于$1000中收益率(降序)为正的

#### 1.1.4.1 基本信息

> 请求路径：/trader/list/00003
>
> 请求方式：GET
>
> 接口描述：该接口用于查询低于$1000中收益率为正的trader，

#### 1.1.4.2 请求参数

无



#### 1.1.4.3 响应数据

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
        "copiers":22
  	}
  ]
}
```



### 1.1.5 查询投资最多中收益率(降序)为正的

#### 1.1.5.1 基本信息

> 请求路径：/trader/list/00004
>
> 请求方式：GET
>
> 接口描述：该接口用于查询投资最多的trader中收益率为正的，

#### 1.1.5.2 请求参数

无



#### 1.1.5.3 响应数据

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
        "copiers":22
  	}
  ]
}
```



### 1.1.6 查询最近两年内收益率(降序)为正的

#### 1.1.6.1 基本信息

> 请求路径：/trader/list/00005
>
> 请求方式：GET
>
> 接口描述：该接口用于查询最近两年内收益率为正的trader

#### 1.1.6.2 请求参数

无



#### 1.1.6.3 响应数据

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
        "ROI": 22.22%，
        "copiers":22
  	}
  ]
}
```



### 1.1.7 查询最近一月内收益率(降序)为正的

#### 1.1.7.1 基本信息

> 请求路径：/trader/list/00006
>
> 请求方式：GET
>
> 接口描述：该接口用于查询最近一月内收益率为正的trader

#### 1.1.7.2 请求参数

无



#### 1.1.7.3 响应数据

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
        "ROI": 22.22%，
        "copiers":22
  	}
  ]
}
```

