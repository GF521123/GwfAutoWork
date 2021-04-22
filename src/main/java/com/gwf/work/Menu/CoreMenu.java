package com.gwf.work.Menu;

import com.gwf.work.Menu.StartMenu.NeedPendingMenuStart;
import com.gwf.work.entity.SystemInfor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

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

    @Autowired
    private SystemInfor systemInfor;
    @Autowired
    private NeedPendingMenuStart needPendingMenuStart;


    @Override
    public void run(String... args) throws Exception {
//        synchronized(systemInfor) {
//            if (null==systemInfor.getCoreTime() ) {
//                try {
//                    systemInfor.wait();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }



    }
}
