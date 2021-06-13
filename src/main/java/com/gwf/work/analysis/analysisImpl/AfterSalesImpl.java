package com.gwf.work.analysis.analysisImpl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gwf.work.analysis.AfterSales;
import com.gwf.work.mode.HttpClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/23 9:10
 */
@Repository
public class AfterSalesImpl implements AfterSales {
    private static final Logger log = LoggerFactory.getLogger(AfterSalesImpl.class);

    private String url = "https://www.tf0914.com/dingdan/findShopKeyWord";
    private String params = "";
    private int tongjuNum = 0;
    @Autowired
    private HttpClientRequest httpClientRequest;

    @Override
    public Map<String, String> getafterSalesInfor() {
        tongjuNum = 0;
        params = "keyword=&pagestart=1&status=b50&price1=&price2=";
        String res = httpClientRequest.HttpClientX_www(url, params);

        return shopAfterOne(res);
    }

    public Map<String, String> shopAfterOne(String res) {
        Map<String, String> resultMap = new HashMap<String, String>();

        String resultString = rsultFrist(res);
        if ("".equals(resultString) || null == resultString) {
            resultMap.put("tongjuNum", "0");
            return resultMap;
        }


        int all_shopAfter = 0;
        try {
            JSONObject json_Data = (JSONObject) JSONObject.parse(res);
            JSONObject jsonShopAfter = (JSONObject) JSONObject.parse(json_Data.getString("data"));

            JSONObject json_Order_count = (JSONObject) JSONObject.parse(jsonShopAfter.getString("count"));
            all_shopAfter = (int) json_Order_count.get("b50");
            int pageNums = (int) jsonShopAfter.get("allPagesNum");
            for (int i = 2; i <= pageNums; i++) {
                params = "keyword=&pagestart=" + i + "&status=f30&price1=&price2=&PageSize=30";
                res = httpClientRequest.HttpClientX_www(url, params);
                resultString += rsultFrist(res);
            }
        } catch (Exception e) {

            resultMap.put("status", "200");
            resultMap.put("erInfor", "运行错误");
            e.printStackTrace();
            return resultMap;
        }
        resultString = "<div style='color:red'>【售后订单】汇总：" + tongjuNum + "/" + all_shopAfter + "</div>" + resultString;
        resultMap.put("searchValue", tongjuNum + "/" + all_shopAfter);
        resultMap.put("tongjuNum", "" + tongjuNum);
        resultMap.put("htmlEmailValue", resultString);
        resultMap.put("status", "0");
        resultMap.put("erInfor", "");
        return resultMap;
    }


    public String rsultFrist(String res) {
        JSONObject json_Data = (JSONObject) JSONObject.parse(res);
        JSONObject json_ShopAfter_Infor = (JSONObject) JSONObject.parse(json_Data.getString("data"));
        JSONArray json_ShopAfter_List = json_ShopAfter_Infor.getJSONArray("list");
        if ("".equals(json_ShopAfter_List) || json_ShopAfter_List == null) {
            return "";
        }
        return ergodicShopAfter(json_ShopAfter_List);
    }

    public String ergodicShopAfter(JSONArray json_ShopAfter_List) {
        if ("".equals(json_ShopAfter_List) || json_ShopAfter_List == null) {
            return "";
        } else {
            String emailValueString = "";
            for (int i = 0; i < json_ShopAfter_List.size(); i++) {
                JSONObject ShopAfter_infor = (JSONObject) JSONObject.parse(json_ShopAfter_List.getString(i));
                JSONArray After_infor = (JSONArray) JSONObject.parse(ShopAfter_infor.getString("goodlist"));
                String AfterSuoId = (String) ((JSONObject) After_infor.get(0)).getString("suoId");
                String findAfterUrl = "https://www.tf0914.com/Aftersale/findDDGoodsAftersale";
                String findAfterParams = "{\"suoId\":\"" + AfterSuoId + "\"}";
                JSONObject After_Data = (JSONObject) JSONObject.parse(httpClientRequest.HttpClientJson(findAfterUrl, findAfterParams));
                JSONArray aftersaleLogList = (JSONArray) JSONObject.parse(((JSONObject) JSONObject.parse(((JSONArray) JSONObject.parse(After_Data.getString("data"))).getString(0))).getString("aftersaleLogList"));

                String logtime = ((JSONObject) aftersaleLogList.get(0)).getString("logtime");
                String shopName = ((JSONObject) After_infor.get(0)).getString("aidname");
                String orderName = ((JSONObject) After_infor.get(0)).getString("gname");
                String resValue = ThreeTime(logtime);
                if (!"".equals(resValue)) {
                    emailValueString += "<div>" + shopName + "(" + orderName + ")" + resValue + "</div>";
                }
            }
            return emailValueString;
        }
    }

    public String ThreeTime(String AfterTime) {
        Date date1 = null;
        DateFormat df2 = null;
        try {
            AfterTime = AfterTime.replace("Z", " UTC");
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
            Date date = df.parse(AfterTime);
            SimpleDateFormat df1 = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK);
            date1 = df1.parse(date.toString());
            df2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            df2.format(date1);
            return JoinTimeAfter(date1);
        } catch (ParseException e) {
            e.printStackTrace();
            return "";
        }
    }

    public String JoinTimeAfter(Date shopAfterTime) {
        Date systemTime = new Date();
        int days = (int) ((systemTime.getTime() - shopAfterTime.getTime()) / (1000 * 3600 * 24));
        int temp = (int) ((systemTime.getTime() - shopAfterTime.getTime()) / 1000 / 60);// 分
        int fen = (int) temp % 60;
        temp /= 60;// 时
        int shi = (int) temp % 24;
        if (days >= 3) {
            tongjuNum++;
            return days + " 天 " + shi + " 时 " + fen + " 分";
        } else {
            return "";
        }

    }
}
