package com.gwf.work.Menu;

import com.gwf.work.Menu.StartMenu.OrderInforMenuStart;
import com.gwf.work.entity.SystemInfor;
import com.gwf.work.utils.GwfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/16 9:18
 */

@Component
@Order(value=3)
public class OrderInforStartMenu  implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(OrderInforStartMenu.class);

    @Autowired
    private SystemInfor systemInfor;
    @Autowired
    private OrderInforMenuStart orderInforMenuStart;



    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(){
            public void run() {
                synchronized(systemInfor) {
                    if (null == systemInfor.getSeparatedTime()) {
                        try {
                            systemInfor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                int SeparatedTime = Integer.parseInt(systemInfor.getSeparatedTime());
                int second = 0;
                    while (true) {
                        second++;
                        try {
                            synchronized (systemInfor) {
                                if (!systemInfor.getStatus()) {
                                    systemInfor.wait();
                                }
                                systemInfor.setStatus(false);
                                orderInforMenuStart.startMenu(second);
                                systemInfor.setStatus(true);
                            }
                            Thread.sleep(SeparatedTime * 60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }

                    }
            }
        }.start();
    }
}

