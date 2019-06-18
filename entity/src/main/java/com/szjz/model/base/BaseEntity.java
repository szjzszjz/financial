package com.szjz.model.base;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * author:szjz
 * date:2019/6/17
 */

@Data
@MappedSuperclass
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = -8309439853320472662L;

//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(columnDefinition = "int(11) COMMENT '自增策略'")
//    private Long id;

//    /** 是否已删除 */
//    @Column(columnDefinition = "bit(1) COMMENT '默认：false  删除：true'")
//    private Boolean isDeleted = false;

    //创建时间以当前时间为准 不可插入 不可更新
    @Column(nullable = false,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'",insertable = false,updatable = false)
    private Date createTime;

    //更新时间以当前时间为准 不可插入 默认可更新
    @Column(nullable = false,columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间'",insertable = false)
    private Date updateTime;

    /** 备注 */
    private String remark;

}
