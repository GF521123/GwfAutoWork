package com.gwf.work.Menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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



    /*
     *	初始化及总开关实现
     *  子开关1：PrintSystemInfor()输出系统信息
     *  子开关2：GoodsToBeSend()待发订单监控
     *  子开关end： KeepCookies()维持cookies不会失效
     */
    @Override
    public void run(String... args) throws Exception {

    }

}
