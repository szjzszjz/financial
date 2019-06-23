package com.szjz.seller.sign;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.hazelcast.util.JsonUtil;
import net.sf.json.JSONObject;

/**
 * author:szjz
 * date:2019/6/22
 *签名明文
 */
@JsonInclude(JsonInclude.Include.NON_NULL)  //不包含空字符串
@JsonPropertyOrder(alphabetic = true)  //按照属性的字母排序
public interface SignText {
    default String toText(){
        return JSONObject.fromObject(this).toString();
    }
}
