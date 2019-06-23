# financial
金融理财系统  
项目利用gradle 分模块进行构建

## 项目知识点
![思维导图](https://github.com/o0Y0o/financial/blob/master/manager/src/main/resources/static/pic/01.png)  

## 数据库表
[数据库表格](https://github.com/szjzszjz/financial/blob/master/manager/src/main/resources/static/sql/financial.sql)

## 知识点  
- #### RSA  
RAS解密解密：  
是一种非对称加密算法，所谓非对称就是指该算法需要一对秘钥，使用其中一个加密，需要另外一个才能解密。秘钥分为公钥和私钥，私钥是自己保存，公钥提供给对方。  
RSA加签验签：  
使用私钥将明文进行签名生成密文串与明文一起传输。对方收到数据后使用公钥对明文和密文串进行验签，如果验签通过就说明第一数据没有被修改过，第二这些数据一定是持有私钥的人发送的，因为私钥只有自己持有，这就起到了防抵赖的作用。

![RSA](https://github.com/o0Y0o/financial/blob/master/manager/src/main/resources/static/pic/rsa.png)
