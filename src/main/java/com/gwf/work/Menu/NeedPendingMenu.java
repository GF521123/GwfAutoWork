package com.gwf.work.Menu;

import com.gwf.work.Menu.StartMenu.NeedPendingMenuStart;
import com.gwf.work.Menu.StartMenu.OrderInforMenuStart;
import com.gwf.work.entity.SystemInfor;
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
 * @date 2021/4/16 17:52
 */

@Component
@Order(value=4)
public class NeedPendingMenu implements ApplicationRunner {
    private static final Logger log = LoggerFactory.getLogger(NeedPendingMenu.class);

    @Autowired
    private SystemInfor systemInfor;
    @Autowired
    private NeedPendingMenuStart needPendingMenuStart;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        new Thread(){
            public void run() {
//
                synchronized(systemInfor) {
                    if (null == systemInfor.getPendEffectiveTime() ) {
                        try {
                            systemInfor.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                int PendEffectiveTime = Integer.parseInt( systemInfor.getPendEffectiveTime());
                int second = 0;
                while (true) {

                        try {
                            second++;
                            synchronized(systemInfor) {
                                if (!systemInfor.getStatus()) {
                                    systemInfor.wait();
                                }
                                systemInfor.setStatus(false);
                                needPendingMenuStart.startMenu(second);
                                systemInfor.setStatus(true);
                            }

                            Thread.sleep(PendEffectiveTime * 60 * 1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                }



            }
        }.start();
    }
}
