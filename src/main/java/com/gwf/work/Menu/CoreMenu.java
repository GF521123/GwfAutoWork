package com.gwf.work.Menu;

import com.gwf.work.Menu.StartMenu.*;
import com.gwf.work.analysis.AfterSales;
import com.gwf.work.entity.SystemInfor;
import com.gwf.work.entity.ToEmail;
import com.gwf.work.mode.SeleniumHtmlCookie;
import com.gwf.work.mode.SendEmail;
import com.gwf.work.utils.GwfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 * 订单启动控制菜单
 */
@Component
@Order(value = 1)
public class CoreMenu implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(CoreMenu.class);


    private static String emailSubject = "";

    @Autowired
    private SystemInfor systemInfor;
    @Autowired
    private NeedPendingMenuStart needPendingMenuStart;
    @Autowired
    private OrderInforMenuStart orderInforMenuStart;
    @Autowired
    private SendEmail emailUtils;
    @Autowired
    ToEmail toEmail;
    @Autowired
    GwfUtils gwfUtils;
    @Autowired
    private SystemInforMenuStart systemInforMenuStart;
    @Autowired
    private UpdateShopNameMenuStart updateShopNameMenuStart;
    @Autowired
    private SeleniumHtmlCookie seleniumHtmlCookie;
    @Autowired
    private ShopPendingMenuStart shopPendingMenuStart;
    @Autowired
    private AfterSalesMenuStart afterSalesMenuStart;

    @Override
    public void run(String... args) throws Exception {
        new Thread() {
            public void run() {
                systemInforMenuStart.showSystemInfor();

                synchronized (systemInfor) {
                    if (null == systemInfor.getCoreTime()) {
                        try {
                            systemInfor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }

                int second = 0;
                runCore(second);
            }
        }.start();
    }

    public void runCore(int second) {

        String afterResString = "";
        String orderResString = "";
        String NeedPendResString = "";
        String ShopResString = "";
        String UpdateNameResString = "";
        if (gwfUtils.isRunTime()) {
            systemInfor.setCookie(seleniumHtmlCookie.getHtmlCookie());
            systemInfor.setSyncNum(0);
            second++;
            String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
            emailSubject = timeStr1 + " 第" + second + "次检索";
            log.info("------------------" + timeStr1 + " 执行第" + second + "次检索" + "-------------------");
            try {
                //待发订单
                synchronized (systemInfor) {
                    orderResString = getRestValue(systemInfor, "待发订单");
                }
                //待审商品
                synchronized (systemInfor) {
                    NeedPendResString = getRestValue(systemInfor, "待审商品");
                }

                //待改名字
                synchronized (systemInfor) {
                    UpdateNameResString = getRestValue(systemInfor, "改名店名");
                }
                synchronized (systemInfor) {
                    ShopResString = getRestValue(systemInfor, "待审店铺");
                }

                synchronized (systemInfor) {
                    afterResString = getRestValue(systemInfor, "售后订单");
                }

                synchronized (systemInfor) {
                    if (systemInfor.getSyncNum() != 5) {
                        systemInfor.wait();
                    }
                    String resValue = ShopResString + UpdateNameResString + NeedPendResString + afterResString + orderResString;
                    if (!"".equals(resValue)) {
                        log.info("+++++++++++++++++++++++++++++++【邮件】++++++++-+++++++++++++++++++++");
                        log.info("【邮件】检索完毕，开始发送邮件");
                        toEmail.setSubject(emailSubject);
                        toEmail.setContent(resValue);
                        log.info(emailUtils.htmlEmail(toEmail));
                        log.info("---------------------------------END--------------------------------");
                    }
                }
                Thread.sleep(Integer.parseInt(systemInfor.getCoreTime()) * 60 * 1000);
                runCore(second);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else {
            try {
                Thread.sleep(Integer.parseInt(systemInfor.getCoreTime()) * 60 * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            runCore(second);

        }
    }

    public String getRestValue(SystemInfor systemInfor, String name) {
        synchronized (systemInfor) {

            if (!systemInfor.getStatus()) {
                try {
                    systemInfor.wait();
                } catch (Exception e) {

                }
            }
            String resValue = "";
            systemInfor.setStatus(false);
            gwfUtils.ConsoleHead(name);
            if ("待发订单".equals(name)) {
                resValue = orderInforMenuStart.startMenu();
            } else if ("待审商品".equals(name)) {
                resValue = needPendingMenuStart.startMenu();
            } else if ("改名店名".equals(name)) {
                resValue = updateShopNameMenuStart.startMenu();
            } else if ("待审店铺".equals(name)) {
                resValue = shopPendingMenuStart.startMenu();
            } else if ("售后订单".equals(name)) {
                resValue = afterSalesMenuStart.startMenu();

            }
            systemInfor.setSyncNum(systemInfor.getSyncNum() + 1);
            gwfUtils.ConsoleEnd(name);
            systemInfor.setStatus(true);
            return resValue;
        }
    }
}
