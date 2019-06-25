package com.szjz.seller.service.Impl;

import com.szjz.seller.service.VerificationOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/23
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class VerificationOrderServiceImplTest {

    @Resource
    private VerificationOrderService orderService;

    @Test
    public void makeVerificationFile() {

        GregorianCalendar day = new GregorianCalendar(2019, 5, 23);

    }

    @Test
    public void verifyOrder() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date day1 = format.parse("2019-6-25");
        List<String> list = orderService.verifyOrder("bbb", day1);
        System.err.println(list);
    }
}