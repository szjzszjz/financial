package com.szjz.result;

import lombok.Getter;

/**
 * author:szjz
 * date:2019/6/18
 */
@Getter
public enum ResultEnum {
    /** shift + ctrl + u 大小写相互转换*/
    SUCCESS(200, "操作成功"),
    FAIL(1, "操作失败"),
    SYSTEM_ERROR(2,"系统错误"),
    SESSION_TIMEOUT(3, "未登录/登录超时"),
    AUTH_ACCOUNT_DISABLED(4, "用户被禁用"),
    AUTH_ACCOUNT_FORBIDDEN(5, "权限不足"),
    SYSTEM_USER_TYPE_ERROR(6,"登录用户类型错误"),

    CREATE_FAIL(50,"创建失败"),
    UPDATE_FAIL(51,"更新失败"),
    DELETE_SUCCESS(52,"删除成功"),
    ID_CANT_EMPTY(54,"id不能为空"),
    RECORD_DOES_NOT_EXIST(56,"记录不存在"),
    RECORD_HAVE_EXIST(56,"记录已经存在"),

    LOGIN_ERROR_USER_NOT_EXIST(130,"登录异常用户不存在"),

    LOGOUT_SUCCESS(131,"退出成功"),
    LOGOUT(132,"已经退出")
    ;

    private Integer code;
    private String msg;

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ResultEnum getEnumByMsg(String msg){
        for (ResultEnum e:ResultEnum.values()){
            if (e.getMsg().equals(msg)){
                return e;
            }
        }
        return null;
    }

    public static ResultEnum getEnumByCode(Integer code){
        for (ResultEnum e:ResultEnum.values()){
            if (e.getCode()==code){
                return e;
            }
        }
        return null;
    }

}
