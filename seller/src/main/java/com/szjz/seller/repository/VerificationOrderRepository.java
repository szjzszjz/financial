package com.szjz.seller.repository;

import com.szjz.model.VerificationOrder;
import com.szjz.model.base.BaseRepository;
import org.springframework.data.jpa.repository.Query;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * author:szjz
 * date:2019/6/23
 */
public interface VerificationOrderRepository extends BaseRepository<VerificationOrder> {

    @Query(value = "select concat_ws('|',id,outer_order_id,chan_id,chan_user_id,product_id,order_type,amount,date_format(create_time,'%Y-%m-%d %H:%i:%s')) " +
            "from order_t " +
            "where order_status = '2' " +
            "and chan_id = ?1 " +
            "and create_time >= ?2 " +
            "and create_time < ?3",nativeQuery = true)
    List<String > queryVerificationOrders(String chanId, Date start, Date end);


}
