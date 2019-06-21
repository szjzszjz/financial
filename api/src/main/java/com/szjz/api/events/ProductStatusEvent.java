package com.szjz.api.events;

import com.szjz.enums.ProductStatusEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * author:szjz
 * date:2019/6/21
 * 产品状态事件
 */

@Data
public class ProductStatusEvent implements Serializable {

    /** 产品编号 */
    private String id;

    /** 产品状态 */
    private ProductStatusEnum status;


    public ProductStatusEvent() {
    }

    public ProductStatusEvent(String id, ProductStatusEnum status) {
        this.id = id;
        this.status = status;
    }
}
