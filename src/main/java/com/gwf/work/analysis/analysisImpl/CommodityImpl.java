package com.gwf.work.analysis.analysisImpl;

import com.alibaba.fastjson.JSONObject;
import com.gwf.work.mode.SendEmail;
import com.gwf.work.entity.ToEmail;
import com.gwf.work.mode.HttpClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.gwf.work.analysis.Commodity;
@Repository
public class CommodityImpl implements Commodity {
	private static final Logger log = LoggerFactory.getLogger(CommodityImpl.class);
	
	private String url = "https://www.tf0914.com/goods/selectByExampleAndPageDetail";
	private String params = "{\"entity\":{\"status\":\"待审核\"},\"pageNum\":1,\"pageSize\":10,\"jcls\":\"Goods\"}";
	private int totalRows;
	@Autowired
	SendEmail sendEmail;
	@Autowired
	HttpClientRequest HttpClientRequest;

	@Autowired
	private ToEmail toEmail;

	public String PendSendEmailController(int PendCommodityTime) {
		boolean sta =  CommodityJson(HttpClientRequest.HttpClientJson(url, params ));
		if(sta) {
			toEmail.setSubject("");
			sendEmail.htmlEmail(toEmail);
			return "待审商品"+totalRows+"个，发送邮件";
		}else {
			return "无待审商品，不发送邮件";
			
		}
	}
	public boolean CommodityJson(String httpClientJson) {
		JSONObject json_Data = (JSONObject) JSONObject.parse(httpClientJson);
		totalRows = (int) JSONObject.parse(json_Data.getString("totalRows"));
		if(totalRows==0) {
			log.info("无待审商品");
			return false;
		}else {
			log.info("有待审商品 "+totalRows+" 个");
			return true;
		}
	}

}
