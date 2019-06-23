package com.szjz.seller.service.Impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.GregorianCalendar;

/**
 * author:szjz
 * date:2019/6/23
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class VerificationOrderServiceImplTest {

    @Resource
//    private VerificationOrderService orderService;

    @Test
    public void makeVerificationFile() {

        GregorianCalendar day = new GregorianCalendar(2019, 5, 23);

    }
}