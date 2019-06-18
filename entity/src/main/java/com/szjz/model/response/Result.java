package com.szjz.model.response;

import com.szjz.model.base.PageModel;
import lombok.Data;
import org.springframework.data.domain.Page;

/**
 * author:szjz
 * date:2019/6/18
 */

@Data
public class Result {

    private static final long serialVersionUID = -4356140312569739366L;
    /**
     * 错误码
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String msg;

    /**
     * 返回的数据
     */
    private Object data;

    /**
     * debug模式下, 同时返回错误堆栈
     */
    private String exMsg;

    public Result() {
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Result(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    //-----------------------------操作成功-----------------------------------------

    /**
     * 无参
     */
    public static Result success() {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg());
    }

    /**
     * 自定义操作信息
     */
    public static Result success(String msg) {
        return new Result(ResultEnum.SUCCESS.getCode(), msg);
    }

    /**
     * 获取数据返回
     */
    public static Result success(Object data) {
        return new Result(ResultEnum.SUCCESS.getCode(), ResultEnum.SUCCESS.getMsg(), data);
    }

    /**
     * 枚举信息
     */
    public static Result success(ResultEnum resultEnum) {
        return new Result(resultEnum.getCode(), resultEnum.getMsg());
    }

    /**
     * 分页
     */
    public static Result success(Page page) {
        PageModel pageModel = new PageModel();
        pageModel.setTotalElements(page.getTotalElements());
        pageModel.setTotalPages(page.getTotalPages());
        pageModel.setPageSize(page.getSize());
        pageModel.setPageContent(page.getContent());
        return Result.success(pageModel);
    }

    //-----------------------------操作失败-----------------------------------------

    /**
     * 无参
     */
    public static Result fail() {
        return new Result(ResultEnum.FAIL.getCode(), ResultEnum.FAIL.getMsg());
    }

    /**
     * 自定义错误信息
     */
    public static Result fail(String failMsg) {
        return new Result(ResultEnum.FAIL.getCode(), failMsg);
    }

    /**
     * 自定义错误码和错误信息
     */
    public static Result fail(Integer code, String failMsg) {
        return new Result(code, failMsg);
    }

    /**
     * 返回错误 枚举信息
     */
    public static Result fail(ResultEnum resultEnum) {
        return new Result(resultEnum.getCode(), resultEnum.getMsg());
    }

    /**
     * 系统错误
     */
    public static Result error() {
        return error(ResultEnum.SYSTEM_ERROR.getMsg());
    }

    /**
     * 系统错误
     */
    public static Result error(String errorMsg) {
        return error(ResultEnum.SYSTEM_ERROR.getCode(), errorMsg);
    }

    /**
     * 系统错误
     */
    public static Result error(int code, String errorMsg) {
        return new Result(code, errorMsg);
    }

    /**
     * session超时/登录状态失效
     */
    public static Result sessionTimeout() {
        return new Result(ResultEnum.SESSION_TIMEOUT.getCode(), ResultEnum.SESSION_TIMEOUT.getMsg());
    }

    /**
     * session超时/登录状态失效 并返回登录链接
     */
//    public static Result sessionTimeout(String loginUrl) {
//        Result result = sessionTimeout();
//        result.setData(loginUrl);
//        return result;
//    }

    /**
     * 用户被禁用
     */
    public static Result accountDisabled() {
        return new Result(ResultEnum.AUTH_ACCOUNT_DISABLED.getCode(), ResultEnum.AUTH_ACCOUNT_DISABLED.getMsg());
    }

    /**
     * 权限不足
     */
    public static Result forbidden() {
        return new Result(ResultEnum.AUTH_ACCOUNT_FORBIDDEN.getCode(), ResultEnum.AUTH_ACCOUNT_FORBIDDEN.getMsg());
    }

}

