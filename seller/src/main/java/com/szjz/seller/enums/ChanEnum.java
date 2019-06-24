package com.szjz.seller.enums;

import lombok.Getter;

/**
 * author:szjz
 * date:2019/6/23
 */

@Getter
public enum ChanEnum {
    AAA("aaa","AAA","/opt/aaa"),
    BBB("bbb","BBB","/opt/bbb"),
    ;

    private String chanId;
    private String chanName;
    private String rootDir;

    private String ftpUser;
    private String ftpPassword;
    private String ftpPath;

    ChanEnum(String chanId, String chanName, String rootDir) {
        this.chanId = chanId;
        this.chanName = chanName;
        this.rootDir = rootDir;
    }

    public static ChanEnum getByChanId(String chanId){
        for (ChanEnum chanEnum : ChanEnum.values()) {
            if (chanEnum.getChanId().equals(chanId)){
                return chanEnum;
            }
        }
        return null;
    }
}
