package com.szjz.seller.service;

import com.szjz.model.base.BaseService;
import com.szjz.model.Sign;

/**
 * author:szjz
 * date:2019/6/23
 */
public interface SignService extends BaseService<Sign> {

    /** 根据authID获取公钥 */
    String getPublicKey(String authId);
    String getPrivateKey(String authId);
}
