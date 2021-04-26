package com.gwf.work.Menu.StartMenu;

import com.gwf.work.analysis.AfterSales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/23 9:06
 */
@Component
@Repository
public class AfterSalesMenuStart {
    private static final Logger log = LoggerFactory.getLogger(AfterSalesMenuStart.class);

    @Autowired
    private AfterSales afterSales;
    public String startMenu(int second,String timeStr1){
        Map<String, String> ShopAfterInfor = afterSales.getafterSalesInfor();
        if("0".equals(ShopAfterInfor.get("tongjuNum"))){
            log.info("【售后商品】检索"+timeStr1 + " 执行第" + second + "次检索---无需售后订单需要操作");
            return "";
        }else{
            log.info("【售后商品】检索"+timeStr1 + " 执行第" + second + "次检索结果为："+ShopAfterInfor.get("searchValue"));
            return ShopAfterInfor.get("htmlEmailValue");
        }
    }
}
