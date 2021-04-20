package com.gwf.work.Menu;

import com.gwf.work.Menu.StartMenu.SystemInforMenuStart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/15 17:55
 * 订单启动控制菜单
 */
@Component
@Order(value=2)
@Repository
public class SystemInforMenu implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(SystemInforMenu.class);

    @Autowired
    private SystemInforMenuStart systemInforMenuStart;

    @Override
    public void run(String... args) throws Exception {
        new Thread(){
            public void run() {
                log.info("系统初始化.....");
                systemInforMenuStart.showSystemInfor();
                log.info("系统初始化结束");
            }
        }.start();

    }
}
