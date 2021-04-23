package com.gwf.work.Menu.StartMenu;

import com.gwf.work.analysis.AfterSales;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/23 9:06
 */
public class AfterSalesMenuStart {
    private static final Logger log = LoggerFactory.getLogger(AfterSalesMenuStart.class);

    @Autowired
    private AfterSales afterSales;
    public String startMenu(int second,String timeStr1){
        String needPendingInforNum = afterSales.getafterSalesInfor();
        if("".equals(needPendingInforNum)){
            log.info("【售后商品】检索"+timeStr1 + " 执行第" + second + "次检索---无需要审核商品");
            return "";
        }else{
            log.info("【售后商品】检索"+timeStr1 + " 执行第" + second + "次检索结果为："+needPendingInforNum);
            return "【售后商品】检索"+timeStr1 + " 执行第" + second + "次检索 "+ needPendingInforNum;
        }
    }
}
