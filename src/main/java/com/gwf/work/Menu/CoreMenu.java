package com.gwf.work.Menu;

import com.gwf.work.Menu.StartMenu.*;
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
@Order(value=1)
public class CoreMenu  implements CommandLineRunner {
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
                String orderResString = "";
                String NeedPendResString = "";
                String ShopResString = "";
                String UpdateNameResString = "";
                while (true) {
                    systemInfor.setCookie(seleniumHtmlCookie.getHtmlCookie());
                    if (gwfUtils.isRunTime()) {
                        systemInfor.setSyncNum(0);
                        second++;
                        String timeStr1 = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
                        emailSubject = timeStr1 + " 第" + second + "次检索";
                        try {
                            //待发订单
                            synchronized (systemInfor) {
                                if (!systemInfor.getStatus()) {
                                    systemInfor.wait();
                                }
                                systemInfor.setStatus(false);
                                orderResString = orderInforMenuStart.startMenu(second,timeStr1);
                                systemInfor.setSyncNum(systemInfor.getSyncNum() + 1);
                                systemInfor.setStatus(true);
                            }
                            //待审商品
                            synchronized (systemInfor) {
                                if (!systemInfor.getStatus()) {
                                    systemInfor.wait();
                                }
                                systemInfor.setStatus(false);
                                NeedPendResString = needPendingMenuStart.startMenu(second,timeStr1);
                                systemInfor.setSyncNum(systemInfor.getSyncNum() + 1);
                                systemInfor.setStatus(true);
                            }

                            //待改名字
                            synchronized (systemInfor) {
                                if (!systemInfor.getStatus()) {
                                    systemInfor.wait();
                                }
                                systemInfor.setStatus(false);
                                UpdateNameResString = updateShopNameMenuStart.startMenu(second,timeStr1);
                                systemInfor.setSyncNum(systemInfor.getSyncNum() + 1);
                                systemInfor.setStatus(true);
                            }
                            //待改名字
                            synchronized (systemInfor) {
                                if (!systemInfor.getStatus()) {
                                    systemInfor.wait();
                                }
                                systemInfor.setStatus(false);
                                ShopResString = shopPendingMenuStart.startMenu(second,timeStr1);
                                systemInfor.setSyncNum(systemInfor.getSyncNum() + 1);
                                systemInfor.setStatus(true);
                            }

                            synchronized (systemInfor) {
                                if (systemInfor.getSyncNum() != 4) {
                                    systemInfor.wait();
                                }
                                String resValue = ShopResString+ UpdateNameResString + NeedPendResString + orderResString;
                                if (!"".equals(resValue)) {
                                    toEmail.setSubject(emailSubject);
                                    toEmail.setContent(resValue);
                                    log.info(emailUtils.htmlEmail(toEmail));
                                }
                            }
                            Thread.sleep(Integer.parseInt(systemInfor.getCoreTime()) * 60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }

                }
            }
        }.start();
    }
}
