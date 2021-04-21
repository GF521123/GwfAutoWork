package com.gwf.work.Menu.StartMenu;

import com.gwf.work.analysis.OrderInfor;
import com.gwf.work.entity.SystemInfor;
import com.gwf.work.mode.SeleniumHtmlCookie;
import com.gwf.work.mode.SendEmail;
import com.gwf.work.entity.ToEmail;

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
    GwfUtils gwfUtils;

    @Autowired
    private SystemInfor systemInfor;
    @Autowired
    private SeleniumHtmlCookie seleniumHtmlCookie;

    public void startMenu(int second){
        if (gwfUtils.isRunTime()) {
            systemInfor.setCookie(seleniumHtmlCookie.getHtmlCookie());
            log.info("----------------------------------------------------------");
            log.info("【待发订单检索】结束，待发订单监控模块进入检索状态");
            String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            emailSubject = timeStr1 + " 第" + second + "次检索";
            toEmail.setSubject(emailSubject);
            Map<String, String> resultMap = orderInfor.OrderImpl_XMLHttp();
            if(resultMap.get("status").equals("200")) {
                toEmail.setContent(resultMap.get("erInfor") + ",系统将自动退出");
                log.info("【待发订单检索】" + timeStr1 + " 执行第" + second + " ," + resultMap.get("erInfor") + ",系统将自动退出" + emailUtils.htmlEmail(toEmail));
            }else if("0".equals(resultMap.get("tongjuNum"))){
                log.info("【待发订单检索】"+timeStr1 + " 执行第" + second + "次检索---无待发商品,不发送邮件通知");
            }else{
                toEmail.setContent(resultMap.get("htmlEmailValue"));
                log.info("【待发订单检索】"+timeStr1 + " 执行第" + second + "次检索---结果为："+resultMap.get("searchValue")+","+emailUtils.htmlEmail(toEmail));
            }
            log.info("【待发订单检索】结束，待发订单监控模块进入休眠状态");
            log.info("----------------------------------------------------------");
        }
    }

}
