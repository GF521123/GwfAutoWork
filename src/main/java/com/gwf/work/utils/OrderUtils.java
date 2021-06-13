package com.gwf.work.utils;

/**
 * @author GWF
 */

import java.util.Date;//import org.testng.annotations.Test;

import com.alibaba.fastjson.JSONArray;

public interface OrderUtils {

    /*
     * 格式化数据
     */
    public String rsultFrist(String res);

    /*
     * 遍历处理
     */
    public String ergodic(JSONArray json_Order_List);

    /*
     * 订单条件处理
     */
    public boolean timeSelect(String time);

    public boolean JoinTimeSelect(int select, Date date1, Date date2);

    /**
     * 通过时间秒毫秒数判断两个时间的间隔
     *
     * @param date1
     * @param date2
     * @return
     */
    public String differentDaysByMillisecond(Date date1, Date date2);

    //	文件操作
    public String readCokies();

    //	文件操作
    public String readFile(String path);

}
