package com.heifeng.demo.exception;

/**
 * @Author: XLF
 * @Date: 2021/06/30/17:03
 * @Description: 自定义异常
 */
public class HfException extends RuntimeException {

    private String msg;

    public HfException(String msg){
        super(msg);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
