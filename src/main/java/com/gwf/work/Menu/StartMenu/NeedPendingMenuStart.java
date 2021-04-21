package com.gwf.work.Menu.StartMenu;

import com.gwf.work.analysis.NeedPendingInfor;
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

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 * 订单启动控制菜单
 */
@Component
@Repository
public class NeedPendingMenuStart {
    private static final Logger log = LoggerFactory.getLogger(NeedPendingMenuStart.class);
    private static String emailSubject = "";

    @Autowired
    private NeedPendingInfor needPendingInfor;
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
            String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            systemInfor.setCookie(seleniumHtmlCookie.getHtmlCookie());
            log.info("----------------------------------------------------------");
            log.info("【待审商品检索】激活，待审商品监控模块进入检索状态");
            emailSubject ="【待审商品检索】"+ timeStr1 + " 第" + second + "次检索";
            toEmail.setSubject(emailSubject);

            String needPendingInforNum = this.needPendingInfor.getNeedPendingInfor();
            if("".equals(needPendingInforNum)){
                log.info("【待审商品检索】"+timeStr1 + " 执行第" + second + "次检索---无需要审核商品");
            }else{
                log.info("【待审商品检索】"+timeStr1 + " 执行第" + second + "次检索结果为："+needPendingInforNum);
                toEmail.setContent("【待审商品检索】"+timeStr1 + " 执行第" + second + "次检索 "+ needPendingInforNum);
                log.info(emailUtils.htmlEmail(toEmail));
            }
            log.info("【待审商品检索】结束，待审商品监控模块进入休眠状态");
            log.info("----------------------------------------------------------");
        }
    }

}
