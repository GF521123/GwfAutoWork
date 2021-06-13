package com.gwf.work.analysis.analysisImpl;


/**
 * @author GWF
 */

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.alibaba.fastjson.JSONObject;
import com.gwf.work.analysis.OrderInfor;
import com.gwf.work.utils.GwfUtils;
import com.gwf.work.mode.HttpClientRequest;
import com.gwf.work.utils.OrderUtils;

import java.util.HashMap;
import java.util.Map;

@Repository("Order")
public class OrderInforImpl implements OrderInfor {
    private String url = "https://www.tf0914.com/dingdan/findShopKeyWord";
    private String params = "";

    private static final Logger log = LoggerFactory.getLogger(OrderInforImpl.class);
    @Autowired
    private GwfUtils gwfUtils;
    @Autowired
    private OrderUtils orderUtils;

    @Autowired
    private HttpClientRequest httpClientRequest;


    @Override
    public boolean OrderImpl_XMLHttpTemp() {
        params = "keyword=&pagestart=1&status=f30&price1=&price2=&PageSize=30";
        String res = httpClientRequest.HttpClientX_www(url, params);
        JSONObject json_Data = (JSONObject) JSONObject.parse(res);
        String json_status = json_Data.getString("status");
        if ("fail".equals(json_status)) {
            log.info("保持cookies有效--失败");
            return false;
        }
        log.info("保持cookies有效--成功");
        return true;
    }

    @Override
    public Map<String, String> OrderImpl_XMLHttp() {
        Map<String, String> errMap = new HashMap<String, String>();
        params = "keyword=&pagestart=1&status=f30&price1=&price2=&PageSize=30";
        String res = httpClientRequest.HttpClientX_www(url, params);
        JSONObject json_Data = (JSONObject) JSONObject.parse(res);
        String json_status = json_Data.getString("status");
        if ("fail".equals(json_status)) {
            errMap.put("status", "200");
            errMap.put("erInfor", "cookies无效");
            return errMap;
        }
        return for_page(res);
    }

    public Map<String, String> for_page(String res) {
        Map<String, String> resultMap = new HashMap<String, String>();

        int tongjuNum = 0, all_order = 0;
        String resultString = orderUtils.rsultFrist(res);
        try {
            tongjuNum = tongjuNum + orderUtils.getClass().getField("tongjuNum").getInt(orderUtils);
            JSONObject json_Data = (JSONObject) JSONObject.parse(res);
            JSONObject json_Order_Infor = (JSONObject) JSONObject.parse(json_Data.getString("data"));

            JSONObject json_Order_count = (JSONObject) JSONObject.parse(json_Order_Infor.getString("count"));
            all_order = (int) json_Order_count.get("f30");
            int pageNums = (int) json_Order_Infor.get("allPagesNum");
            for (int i = 2; i <= pageNums; i++) {
                params = "keyword=&pagestart=" + i + "&status=f30&price1=&price2=&PageSize=30";
                res = httpClientRequest.HttpClientX_www(url, params);
                resultString += orderUtils.rsultFrist(res);
                tongjuNum = tongjuNum + orderUtils.getClass().getField("tongjuNum").getInt(orderUtils);
            }
        } catch (Exception e) {

            resultMap.put("status", "200");
            resultMap.put("erInfor", "运行错误");
            // TODO Auto-generated catch block
            e.printStackTrace();
            return resultMap;
        }
        resultString = "<div style='color:red'>【待发订单】汇总：" + tongjuNum + "/" + all_order + "(当前无效数据55条)" + "</div><br>" + resultString;
        resultMap.put("searchValue", tongjuNum + "/" + all_order);
        resultMap.put("tongjuNum", "" + tongjuNum);
        resultMap.put("htmlEmailValue", resultString);
        resultMap.put("status", "0");
        resultMap.put("erInfor", "");
        return resultMap;
    }
}
