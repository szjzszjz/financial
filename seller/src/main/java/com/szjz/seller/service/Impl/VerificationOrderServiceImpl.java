package com.szjz.seller.service.Impl;

import com.szjz.enums.OrderTypeEnum;
import com.szjz.model.VerificationOrder;
import com.szjz.model.base.BaseServiceImpl;
import com.szjz.seller.enums.ChanEnum;
import com.szjz.seller.repository.VerificationOrderRepository;
import com.szjz.seller.service.VerificationOrderService;
import org.aspectj.util.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * author:szjz
 * date:2019/6/23
 */
@Service
public class VerificationOrderServiceImpl extends BaseServiceImpl<VerificationOrder> implements VerificationOrderService {


    @Resource
    private VerificationOrderRepository verificationOrderRepository;

    /**
     * 文件根目录
     */
//    @Value("${verification.rootDir:/opt/verification}")
//    private String rootDir;

    /**
     * 指定换行符
     */
    private String end_line = System.getProperty("line.separator", "\n");

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private DateFormat dateFormat_detail = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 查询验证订单
     *
     * @param chanId 渠道编号
     * @param start  开始时间
     * @param end    结束时间
     * @return 验证订单集合
     */
    @Override
    public List<String> queryVerificationOrders(String chanId, Date start, Date end) {
        List<String> list = verificationOrderRepository.queryVerificationOrders(chanId, start, end);
        return list;
    }

    /**
     * 生成某个渠道某天的对账文件
     *
     * @param chanId 渠道编号
     * @param day
     * @return 对账文件
     */
    @Override
    public File makeVerificationFile(String chanId, Date day) {
        //根据chanId 获取文件存储的根路径
        ChanEnum chanEnum = ChanEnum.getByChanId(chanId);
        String rootDir = chanEnum.getRootDir();
        File file = getFile(rootDir, chanId, day);
        if (file.exists()) {
            return file;
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //构造起止时间
        String start = dateFormat.format(day);
        Date startDate = null;
        try {
            startDate = dateFormat.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date endDate = new Date(startDate.getTime() + 1000 * 60 * 60 * 24);
        List<String> orderStringList = verificationOrderRepository.queryVerificationOrders(chanId, startDate, endDate);
        //利用换行符将字符串拼接起来 生成文件内容
        String fileContent = String.join(end_line, orderStringList);
        //Write contents to file
        FileUtil.writeAsString(file, fileContent);
        return file;
    }


    /**
     * 获取文件 (全路径)
     */
    public File getFile(String rootDir, String chanId, Date day) {
        //生成文件名
        String fileName = dateFormat.format(day) + "-" + chanId + ".txt";
        //根路径+文件名生成文件
        File file = Paths.get(rootDir, fileName).toFile();
        return file;
    }

    /**
     * 将每一行的数据转换成对象
     *
     * @param line
     */
    public VerificationOrder parseLine(String line) {
        String[] eles = line.split("[*]");
        //id,outer_order_id,chan_id,chan_user_id,product_id,order_type,amount,date_format
        VerificationOrder verificationOrder = new VerificationOrder();
        verificationOrder.setOrderId(eles[0]);
        verificationOrder.setOuterOrderId(eles[1]);
        verificationOrder.setChanId(eles[2]);
        verificationOrder.setChanUserId(eles[3]);
        verificationOrder.setProductId(eles[4]);
        verificationOrder.setOrderType(OrderTypeEnum.getByCode(Integer.valueOf(eles[5])));
        verificationOrder.setAmount(new BigDecimal(eles[6]));
        try {
            verificationOrder.setCreateTime(dateFormat_detail.parse(eles[7]));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return verificationOrder;
    }


    /**
     * 保存验证订单数据
     */
    public void saveVerificationOrder(String chanId, Date day) {
        ChanEnum chanEnum = ChanEnum.getByChanId(chanId);

        //获取文件
        File file = getFile(chanEnum.getRootDir(), chanId, day);
        //不存在就返回
        if (!file.exists()) {
            return;
        }
        //存在就读取
        String content = null;
        try {
            content = FileUtil.readAsString(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //解析内容
        String[] lines = content.split(end_line);
        List<VerificationOrder> orderList = new ArrayList<>();
        //解析行
        for (String line : lines) {
            orderList.add(parseLine(line));
        }
        verificationOrderRepository.saveAll(orderList);
    }
}
