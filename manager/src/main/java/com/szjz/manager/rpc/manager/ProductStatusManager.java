package com.szjz.manager.rpc.manager;

import com.szjz.api.events.ProductStatusEvent;
import com.szjz.enums.ProductStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * author:szjz
 * date:2019/6/21
 *
 * 产品状态管理
 */
@Slf4j
@Component
public class ProductStatusManager {

    //定义消息目的地名 VirtualTopic. 规定固定写法
    public static final String MQ_DESTINATION = "VirtualTopic.PRODUCT_MQ";

    @Resource
    private JmsTemplate jmsTemplate;

    public void changeStatus(String id, ProductStatusEnum statusEnum){
        ProductStatusEvent statusEvent = new ProductStatusEvent(id, statusEnum);
        //将消息发送到目的地

        log.info("send event ={}",statusEvent);
        jmsTemplate.convertAndSend(MQ_DESTINATION,statusEvent);
    }

    @PostConstruct
    public void init(){
        changeStatus("aaa",ProductStatusEnum.IN_SELL);
    }

}
