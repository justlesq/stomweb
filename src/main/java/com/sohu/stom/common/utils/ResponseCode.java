package com.sohu.stom.common.utils;

public enum ResponseCode
{
   /* success(0, "成功"), paramsError(1, "没有任务"), ipError(2, "ip重复"), idfaError(
            3, "idfa重复"), noTask(4, "当天没有任务"), taskError(5, "获取任务异常"), itunesIdError(
            6, "获取账号异常"), noItunesId(7, "账号不足"), illegalDevice(7, "设备非法"), noDevice(
            8, "没有足够的设备"), canLoop(9, "可以继续循环任务"), canNotLoop(10, "不能继续任务"),
    		signatureError(11, "签名错误"),
    noComments(12, "评论内容用完");*/



    /*下单状态*/
    WAIT_SCHEDULE(0, "等待排期"), //等待排期
    EXECUTING(1,"执行中"), //执行中
    FINISH(2,"完成"),//完成
    OVER(3,"已结算"); //已结算







    public int code;
    
    public String msg;
    
    ResponseCode(int code, String msg)
    {
        this.code = code;
        this.msg = msg;
    }
    
}
