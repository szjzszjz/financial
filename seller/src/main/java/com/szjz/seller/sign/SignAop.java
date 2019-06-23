package com.szjz.seller.sign;

import com.szjz.seller.service.SignService;
import com.szjz.utils.rsa.RSAUtil;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.Resource;

/**
 * author:szjz
 * date:2019/6/23
 * 签名切面
 */

@Component
@Aspect
public class SignAop {

    @Resource
    private SignService signService;

    @Before(value = "execution(* com.szjz.seller.controller.*.*(..)) && args(authId,sign,text,..)")
    public void verify(String authId,String sign,SignText text){
        //获取公钥
        String publicKey = signService.getPublicKey(authId);
        //获取私钥
        String privateKey = signService.getPrivateKey(authId);
        //加签生成签名
        String sign1 = RSAUtil.sign(text.toText(), privateKey);
        //利用公钥进行验签
        boolean verify = RSAUtil.verify(text.toText(), sign1, publicKey);
        Assert.isTrue(verify,"验签失败！");

    }

}
