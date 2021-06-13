package com.gwf.work.analysis.analysisImpl;

import com.alibaba.fastjson.JSONObject;
import com.gwf.work.analysis.UpdateShopName;
import com.gwf.work.mode.HttpClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/22 10:42
 */
@Repository
public class UpdateShopNameImpl implements UpdateShopName {

    private String url = "https://www.tf0914.com/shopMsgUpdateLog/searchShopMsgUpdateLogVo";
    private String params = "";

    @Autowired
    private HttpClientRequest httpClientRequest;

    public Map<String, String> getUpdateShopRest() {
        params = "{\"entity\":{\"keyWord\":\"\",\"isNotExamineKey\":\"未审核\",\"infoIsNotExamineKey\":\"企业店\"},\"pageNum\":1,\"pageSize\":10,\"jcls\":\"Shop\"}";
        String res = httpClientRequest.HttpClientJson(url, params);

        return getResMap(res);
    }

    public Map<String, String> getResMap(String res) {
        Map<String, String> resMap = new HashMap<>();

        JSONObject json_Data = (JSONObject) JSONObject.parse(res);
        String totalRows = json_Data.getString("totalRows");
        resMap.put("totalRows", totalRows);
        resMap.put("status", "0");

        return resMap;
    }
}
