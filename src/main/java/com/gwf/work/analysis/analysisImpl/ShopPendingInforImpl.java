package com.gwf.work.analysis.analysisImpl;


import com.alibaba.fastjson.JSONObject;
import com.gwf.work.analysis.ShopPendingInfor;
import com.gwf.work.mode.HttpClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/21 15:09
 */

@Repository("ShopPendingInfor")
public class ShopPendingInforImpl implements ShopPendingInfor {
    private static final Logger log = LoggerFactory.getLogger(ShopPendingInforImpl.class);

    private String url = "https://www.tf0914.com/Shop/selectByKeyWorld";
    private String params = "";

    @Autowired
    private HttpClientRequest httpClientRequest;

    @Override
    public Map<String, String> getShopPendinginfor() {
        params = "{\"entity\":{\"keyWord\":\"\",\"isNotExamineKey\":\"未审核\",\"infoIsNotExamineKey\":\"企业店\"},\"pageNum\":1,\"pageSize\":10,\"jcls\":\"Shop\"}";
        String res = httpClientRequest.HttpClientJson(url, params);
        return getShopPendingMap(res);
    }

    public Map<String, String> getShopPendingMap(String res) {
        Map<String, String> resMap = new HashMap<>();
        JSONObject json_Data = (JSONObject) JSONObject.parse(res);
        String totalRows = json_Data.getString("totalRows");
        resMap.put("totalRows", totalRows);
        resMap.put("status", "0");
        return resMap;
    }
}
