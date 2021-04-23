package com.gwf.work.analysis.analysisImpl;

import com.alibaba.fastjson.JSONObject;
import com.gwf.work.analysis.AfterSales;
import com.gwf.work.mode.HttpClientRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/23 9:10
 */
@Repository
public class AfterSalesImpl implements AfterSales {

    private String url = "https://www.tf0914.com/dingdan/findShopKeyWord";
    private String params = "";

    @Autowired
    private HttpClientRequest HttpClientRequest;

    @Override
    public String getafterSalesInfor() {
        params="keyword=&pagestart=1&status=b50&price1=&price2=\n";
        String res =HttpClientRequest.HttpClientX_www(url,params);

        return null;
    }
    public String isNoNeed(String res){

        JSONObject json_Data = (JSONObject) JSONObject.parse(res);
//        String totalRows = json_Data.getString("totalRows");
        return "";
    }
}
