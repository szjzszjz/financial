package com.szjz.model;

import com.szjz.model.base.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;
import java.io.Serializable;

/**
 * author:szjz
 * date:2019/6/23
 */

@Data
@Entity
public class Sign extends BaseEntity implements Serializable {


    private static final long serialVersionUID = 3191741030838061239L;
    /** 秘钥 */
    private String privateKey;

    /** 公钥 */
    private String publicKey;

    /** 授权Id */
    private String authId;


}
