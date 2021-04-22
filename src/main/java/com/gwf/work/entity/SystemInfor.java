package com.gwf.work.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/16 14:51
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Repository
public class SystemInfor {

    private Boolean status;//状态
    private String SeparatedTime ;// 60分钟
    private String PendEffectiveTime ;// 60分钟 待审产品
    private String cookie;
    private String ShopPendingTime;
    private String CoreTime;
}
