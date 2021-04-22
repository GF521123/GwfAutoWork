package com.gwf.work.Menu;

import com.gwf.work.Menu.StartMenu.UpdateShopNameMenuStart;
import com.gwf.work.entity.SystemInfor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/22 10:35
 */
@Component
@Order(value=5)
@Repository
public class UpdateShopNameMenu implements ApplicationRunner {
    @Autowired
    private UpdateShopNameMenuStart updateShopNameMenuStart;
    @Autowired
    private SystemInfor systemInfor;
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
                            updateShopNameMenuStart.startMenu(second);
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
