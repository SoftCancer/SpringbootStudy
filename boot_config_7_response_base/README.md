## 一、创建多模块项目

## 二、修改端口号
默认端口：8080，修改端口：9090
server.port=9090

修改为随机端口

server.port=${random.int(2048,99999)}


## 三、定义标准数据返回格式基础版

一般的response 的返回格式包含3部分：
    
    1. status：状态值，代表请求的状态结果
    2.response:描述，对本次状态码的描述
    3.data：数据，本次返回的数据
    {
        "status":200,
        "desc":"成功",
        "data"："success"
    }
    