package com.szjz.seller.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * author:szjz
 * date:2019/6/24
 *
 * 定时验证任务
 */

@Component
@Transactional
public class VerifyTask {

    /** 每3秒执行一次 cron表达式必须由6位组成*/
//    @Scheduled(cron = "0/3 * * * * ?")
    public void test(){
        System.out.println("test...");
    }
}
