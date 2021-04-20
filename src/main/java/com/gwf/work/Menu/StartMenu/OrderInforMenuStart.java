package com.gwf.work.Menu.StartMenu;

import com.gwf.work.analysis.OrderInfor;
import com.gwf.work.entity.SystemInfor;
import com.gwf.work.mode.SeleniumHtmlCookie;
import com.gwf.work.mode.SendEmail;
import com.gwf.work.entity.ToEmail;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 * 订单启动控制菜单
 */
@Component
@Repository
public class OrderInforMenuStart   {
    private static final Logger log = LoggerFactory.getLogger(OrderInforMenuStart.class);
    private static String emailSubject = "";

    @Autowired
    private OrderInfor orderInfor;
    @Autowired
    private SendEmail emailUtils;
    @Autowired
    ToEmail toEmail;

    @Autowired
    private SystemInfor systemInfor;
    @Autowired
    private SeleniumHtmlCookie seleniumHtmlCookie;

    public void startMenu(int second){
        Date systemDate = new Date();
        if (systemDate.getHours() >= 8 && systemDate.getHours() <= 18) {
            systemInfor.setCookie(seleniumHtmlCookie.getHtmlCookie());
            log.info("【待发订单检索】");
            String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            emailSubject = timeStr1 + " 第" + second + "次检索";
            toEmail.setSubject(emailSubject);
            log.info(""+timeStr1 + " 执行第" + second + "次检索");
            toEmail.setContent(orderInfor.OrderImpl_XMLHttp());
            log.info(emailUtils.htmlEmail(toEmail));
        } else {
            log.info("不在检索时间范围（6~18）内....."+ " 第" + second + "次检索等待中");
        }
        log.info("本次检索结束，系统进入休眠状态");
        log.info("-----------------------------");
    }

}
