# financial
金融理财系统  
项目利用gradle 分模块进行构建

## 项目知识点
![思维导图](https://github.com/o0Y0o/financial/blob/master/manager/src/main/resources/static/pic/01.png)  

## 数据库表
[数据库表格](https://github.com/szjzszjz/financial/blob/master/manager/src/main/resources/static/sql/financial.sql)

## 知识点   
### 学习项目之前需要先观看以下4个视频（已会者随意）
- #### [新一代构建工具gradle](https://www.imooc.com/learn/833)
- #### [轻松愉快之玩转SpringData](https://www.imooc.com/learn/821)
- #### [Java消息中间件](https://www.imooc.com/learn/856)
- #### [Java定时任务调度工具详解之Quartz篇](https://www.imooc.com/learn/846)  

- #### RSA  
RAS加密解密：  
是一种非对称加密算法，所谓非对称就是指该算法需要一对秘钥，使用其中一个加密，需要另外一个才能解密。秘钥分为公钥和私钥，私钥是自己保存，公钥提供给对方。  
RSA加签验签：  
使用私钥将明文进行签名生成密文串与明文一起传输。对方收到数据后使用公钥对明文和密文串进行验签，如果验签通过就说明第一数据没有被修改过，第二这些数据一定是持有私钥的人发送的，因为私钥只有自己持有，这就起到了防抵赖的作用。

![RSA](https://github.com/o0Y0o/financial/blob/master/manager/src/main/resources/static/pic/rsa.png)
- #### 对账  
长款：`order_t`表中存在的订单数据 ，但`verification_order`表中不存在  
漏单：`order_t`表中不存在的订单数据 ，但`verification_order`表中存在  
不一致：`order_t`,`verification_order`表中相同的订单数据 但是订单的部分信息不一致  
平账：收到异常对账结果，通知人工邮件短信  
轧差：申购金额总和与赎回金额总和的差值  
![对账](https://github.com/o0Y0o/financial/blob/master/manager/src/main/resources/static/pic/duizhang.png)

### 黑科技
为了实现自己的个性化需求有时需要修改源码：  
修改源码的两种方案：  
- 下载源码 、修改、编译、测试、提交仓库 [jsonrpc4j源码修改实例](https://github.com/szjzszjz/jsonrpc4j)
- 利用原理：“当前应用下有对应类时不会使用依赖包中的类” 在项目中添加对用的类，复制源码，修改需求部分 [repository.config源码修改实例](https://github.com/szjzszjz/financial/blob/master/seller/src/main/java/com/szjz/seller/org/springframework/data/repository/config)
### 版本迭代引起的问题：  
- （1）配置多数据源时，视频中所讲的方法已不适应springboot 2.0.0之后的版本。最好将主备配置分开，不要像视频中所讲的那样放在一起，不然会出现很多问题。正确方法详见：[多数据源配置](https://github.com/szjzszjz/financial/tree/master/seller/src/main/java/com/szjz/seller/configuration)  

