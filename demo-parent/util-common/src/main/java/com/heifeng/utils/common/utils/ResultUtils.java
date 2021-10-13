package com.heifeng.utils.common.utils;


import com.heifeng.utils.common.vo.Result;

/**
 * @Author: xlf
 * @Date: 2021/06/08/11:24
 * @Description: 返回值的工具包
 */
public class ResultUtils<T> {

    private Result<T> result;

    public ResultUtils(){
        result = new Result<>();
        result.setSuccess(true);
        result.setMessage("success");
        result.setCode(200);
    }

    /**
     * 成功返回结果
     * @param t 返回结果
     * @param msg 返回消息
     * @return
     */
    private Result<T> setSuccess(T t,String msg){
        this.result.setData(t);
        this.result.setSuccess(true);
        this.result.setMessage(msg);
        this.result.setCode(200);
        return this.result;
    }

    /**
     * 失败返回结果
     * @param code 响应码
     * @param msg 返回消息
     * @return
     */
    private Result<T> setError(Integer code, String msg){
        this.result.setSuccess(false);
        this.result.setMessage(msg);
        this.result.setCode(code);
        return this.result;
    }

    /**
     * 自定义返回结果集
     * @param t 结果集
     * @param code 响应码
     * @param msg 消息体
     * @return
     */
    private Result<T> setResultData(T t, Integer code,String msg){
        this.result.setData(t);
        this.result.setCode(code);
        this.result.setMessage(msg);
        return this.result;
    }

    public static <T> Result<T> OK(){
        return new ResultUtils<T>().setSuccess(null,"success");
    }
    public static <T> Result<T> OK(T t,String msg){
        return new ResultUtils<T>().setSuccess(t,msg);
    }
    public static <T> Result<T> OK(T t){
        return new ResultUtils<T>().setSuccess(t,"success");
    }
    public static <T> Result<T> OK(String msg){
        return new ResultUtils<T>().setSuccess(null,msg);
    }

    public static <T> Result<T> ERROR(){
        return new ResultUtils<T>().setError(500,"error");
    }
    public static <T> Result<T> ERROR(Integer code,String msg){
        return new ResultUtils<T>().setError(code,msg);
    }
    public static <T> Result<T> ERROR(String msg){
        return new ResultUtils<T>().setError(500,"error");
    }

    public static <T> Result<T> DATA(T t, String msg){
        return new ResultUtils<T>().setResultData(t, 200,msg);
    }

    public static <T> Result<T> DATA(T t,Integer code,String msg){
        return new ResultUtils<T>().setResultData(t, code,msg);
    }

    /**
     * 无权限访问返回结果
     */
    public static <T> Result<T> NO_AUTH(String msg) {
        return ERROR(403,msg);
    }
    public static <T> Result<T> NO_AUTH() {
        return ERROR(403,"访问权限不足");
    }

}
