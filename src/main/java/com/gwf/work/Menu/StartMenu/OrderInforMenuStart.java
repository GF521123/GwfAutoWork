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
public class OrderInforMenuStart {
    private static final Logger log = LoggerFactory.getLogger(OrderInforMenuStart.class);

    @Autowired
    private OrderInfor orderInfor;
    @Autowired
    ToEmail toEmail;
    @Autowired
    GwfUtils gwfUtils;

    public String startMenu() {

        Map<String, String> resultMap = orderInfor.OrderImpl_XMLHttp();
        if (resultMap.get("status").equals("200")) {
            log.info("【待发订单】检索：" + resultMap.get("erInfor") + ",系统将自动退出");
            return resultMap.get("erInfor") + ",系统将自动退出";
        } else if ("0".equals(resultMap.get("tongjuNum"))) {
            log.info("【待发订单】检索：无待发商品,不发送邮件通知");
            return "";
        } else {
            log.info("【待发订单】检索：" + resultMap.get("searchValue") + ",");
            return resultMap.get("htmlEmailValue");
        }

    }

}
