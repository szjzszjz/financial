package com.szjz.seller.service.Impl;


import com.szjz.model.base.BaseServiceImpl;
import com.szjz.seller.repository.SignRepository;
import com.szjz.seller.service.SignService;
import com.szjz.model.Sign;
import com.szjz.utils.rsa.RSAUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

/**
 * author:szjz
 * date:2019/6/23
 */

@Service
public class SignServiceImpl extends BaseServiceImpl<Sign> implements SignService {

    @Resource
    private SignRepository signRepository;

    /** 获取公钥 */
    @Override
    public String getPublicKey(String authId) {
        Sign sign1 = signRepository.findByAuthId(authId);
        if (sign1 == null) {
            saveKey(authId);
            return signRepository.findByAuthId(authId).getPublicKey();
        } else {
            return sign1.getPublicKey();
        }
    }

    /** 获取私钥 */
    @Override
    public String getPrivateKey(String authId) {
        Sign sign1 = signRepository.findByAuthId(authId);
        if (sign1 == null) {
            saveKey(authId);
            return signRepository.findByAuthId(authId).getPrivateKey();
        } else {
            return sign1.getPrivateKey();
        }
    }

    public void saveKey(String authId){
        //模拟生成公钥私钥
        Map map = null;
        try {
            map = RSAUtil.genKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        Sign sign = new Sign();
        sign.setAuthId(authId);
        sign.setPrivateKey((String) map.get(1));
        sign.setPublicKey((String) map.get(0));
        signRepository.saveAndFlush(sign);
    }
}
