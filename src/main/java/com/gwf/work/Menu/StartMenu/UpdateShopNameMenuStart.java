package com.gwf.work.Menu.StartMenu;

import com.gwf.work.analysis.UpdateShopName;
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
import java.util.Map;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/22 10:37
 */
@Component
@Repository
public class UpdateShopNameMenuStart {
    private static final Logger log = LoggerFactory.getLogger(UpdateShopNameMenuStart.class);

    @Autowired
    private UpdateShopName updateShopName;
    public String startMenu(){

        Map<String, String> resultMap = updateShopName.getUpdateShopRest();
        if(resultMap.get("status").equals("200")) {
            log.info("【改名店名】检索："+ resultMap.get("erInfor") + ",系统将自动退出");
            return resultMap.get("erInfor") + ",系统将自动退出";
        }else if("0".equals(resultMap.get("totalRows"))){
            log.info("【改名店名】检索：无改名店铺,不发送邮件通知");
            return "";
        }else{
            log.info("【改名店名】检索："+resultMap.get("totalRows"));
            return "<div style='color:red'>【改名店名】汇总：" + resultMap.get("totalRows")  + "</div>";
        }
    }
}
