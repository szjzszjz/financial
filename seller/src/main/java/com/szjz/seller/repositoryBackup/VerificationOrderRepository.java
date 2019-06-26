package com.szjz.seller.repositoryBackup;

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

    @Query(value = "select CONCAT_WS('*',id,outer_order_id,chan_id,chan_user_id,product_id,order_type,amount,date_format(create_time,'%Y-%m-%d %H:%i:%s')) " +
            "from order_t " +
            "where order_status = '2' " +
            "and chan_id = ?1 " +
            "and create_time >= ?2 " +
            "and create_time < ?3 ",nativeQuery = true)
    List<String > queryVerificationOrders(String chanId, Date start, Date end);

    /** 长款 */
    @Query(value = "select t.id " +
            "from order_t t " +
            "left join verification_order v " +
            "on t.chan_id = ?1 " +
            "and t.outer_order_id = v.order_id " +
            "where v.order_id is null " +
            "and t.create_time >= ?2 " +
            "and t.create_time < ?3 ",nativeQuery = true)
    List<String > queryExcessOrders(String chanId, Date start, Date end);

    /** 漏单 */
    @Query(value = "select v.order_id " +
            "from verification_order v " +
            "left join order_t t " +
            "on v.chan_id = ?1 " +
            "and v.outer_order_id = t.id " +
            "where t.id is null " +
            "and v.create_time >= ?2 " +
            "and v.create_time < ?3 ",nativeQuery = true)
    List<String > queryMissOrders(String chanId, Date start, Date end);

    /** 不一致 */
    @Query(value = "select t.id " +
            "from order_t t " +
            "join verification_order v " +
            "on t.chan_id = ?1 " +
            "and t.outer_order_id = v.order_id " +
            "where " +
            "CONCAT_WS('|',t.chan_id,t.chan_user_id,t.product_id,t.order_type,t.amount,date_format(t.create_time,'%Y-%m-%d %H:%i:%s')) != " +
            "CONCAT_WS('|',v.chan_id,v.chan_user_id,v.product_id,v.order_type,v.amount,date_format(v.create_time,'%Y-%m-%d %H:%i:%s')) " +
            "and t.create_time >= ?2 " +
            "and t.create_time < ?3 ",nativeQuery = true)
    List<String > queryDifferentOrders(String chanId, Date start, Date end);



}