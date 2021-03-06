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

    @Autowired
    private NeedPendingInfor needPendingInfor;
    @Autowired
    ToEmail toEmail;
    @Autowired
    GwfUtils gwfUtils;

    public String startMenu() {
        String needPendingInforNum = needPendingInfor.getNeedPendingInfor();
        if ("".equals(needPendingInforNum)) {
            return "";
        } else {
            log.info("【待审商品】检索：" + needPendingInforNum);
            return "<div style='color:red'>【待审商品】汇总：" + needPendingInforNum + "</div>";
        }
    }

}
