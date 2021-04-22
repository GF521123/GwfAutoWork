package com.gwf.work.Menu.StartMenu;

import com.gwf.work.analysis.OrderInfor;
import com.gwf.work.analysis.ShopPendingInfor;
import com.gwf.work.entity.SystemInfor;
import com.gwf.work.entity.ToEmail;
import com.gwf.work.mode.SeleniumHtmlCookie;
import com.gwf.work.mode.SendEmail;
import com.gwf.work.utils.GwfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/21 14:49
 */
@Component
@Repository
public class ShopPendingMenuStart {
    private static final Logger log = LoggerFactory.getLogger(ShopPendingMenuStart.class);
    private static String emailSubject = "";

    @Autowired
    private ShopPendingInfor shopPendingInfor;
    @Autowired
    private SendEmail emailUtils;
    @Autowired
    ToEmail toEmail;
    @Autowired
    GwfUtils gwfUtils;

    @Autowired
    private SystemInfor systemInfor;
    @Autowired
    private SeleniumHtmlCookie seleniumHtmlCookie;

    public String startMenu(int second,String timeStr1){

        if (gwfUtils.isRunTime()) {
            Map<String, String> resultMap = shopPendingInfor.getShopPendinginfor();
            if(resultMap.get("status").equals("200")) {

                log.info("【待审店铺】检索" + timeStr1 + " 执行第" + second + " ," + resultMap.get("erInfor") + ",系统将自动退出" );
                return  resultMap.get("erInfor") + ",系统将自动退出";
            }else if("0".equals(resultMap.get("totalRows"))){
                log.info("【待审店铺】检索"+timeStr1 + " 执行第" + second + "次检索---无待审店铺,不发送邮件通知");
                return "";
            }else{
                log.info("【待审店铺】检索"+timeStr1 + " 执行第" + second + "次检索---结果为："+resultMap.get("totalRows"));
                return "<div style='color:red'>【待审店铺】汇总：" + resultMap.get("totalRows")  + "</div>";
            }
        }
        return "";
    }
}
