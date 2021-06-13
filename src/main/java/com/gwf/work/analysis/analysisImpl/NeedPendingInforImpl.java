package com.gwf.work.analysis.analysisImpl;

import com.alibaba.fastjson.JSONObject;
import com.gwf.work.mode.SendEmail;
import com.gwf.work.mode.HttpClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gwf.work.analysis.NeedPendingInfor;

@Repository
public class NeedPendingInforImpl implements NeedPendingInfor {
    private static final Logger log = LoggerFactory.getLogger(NeedPendingInforImpl.class);

    private String url = "https://www.tf0914.com/goods/selectByExampleAndPageDetail";
    private String params = "{\"entity\":{\"status\":\"待审核\"},\"pageNum\":1,\"pageSize\":10,\"jcls\":\"Goods\"}";
    private int totalRows = 0;
    @Autowired
    SendEmail sendEmail;
    @Autowired
    HttpClientRequest HttpClientRequest;

    public String getNeedPendingInfor() {
        boolean sta = CommodityJson(HttpClientRequest.HttpClientJson(url, params));
        if (sta) {
            return "" + totalRows;
        } else {
            return "";

        }
    }

    public boolean CommodityJson(String httpClientJson) {
        JSONObject json_Data = (JSONObject) JSONObject.parse(httpClientJson);
        totalRows = (int) JSONObject.parse(json_Data.getString("totalRows"));
        if (totalRows == 0) {
            return false;
        } else {
            return true;
        }
    }

}
