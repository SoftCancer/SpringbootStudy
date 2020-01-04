## 一、创建多模块项目

## 二、修改端口号
默认端口：8080，修改端口：9090
server.port=9090

修改为随机端口

server.port=${random.int(2048,99999)}

## 三、自定义属性配置
在application.properties 文件中配置属性，在类中通过 @Value 获取。
    @Value("${boot.msg}") <br>
    private String msg;

    @GetMapping("/msg")
    public String getMsg(){
        return msg;
    }
 