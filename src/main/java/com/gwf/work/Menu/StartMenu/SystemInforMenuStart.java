package com.gwf.work.Menu.StartMenu;

import com.gwf.work.entity.SystemInfor;
import com.gwf.work.utils.GwfUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;


/**
 * @author GWF
 */

@Component
@Repository
public class SystemInforMenuStart{
	private static final Logger log = LoggerFactory.getLogger(SystemInforMenuStart.class);

	@Autowired
	private GwfUtils gwfUtils;
	@Autowired
	private SystemInfor systemInfor;

	public void showSystemInfor() {
		String sysInforString=ShowSystemInfor()+"\n\n--------------当前运行环境信息----------------\n";
		sysInforString=sysInforString + "获取系统运行环境信息...";
		sysInforString=sysInforString + "操作系统：" + System.getProperty("os.name")+"\n";// 操作系统的名称
		sysInforString=sysInforString + "系统版本：" + System.getProperty("os.version")+"\n";//  操作系统的版本号
		sysInforString=sysInforString + "系统架构：" + System.getProperty("os.arch")+"\n";//  操作系统的架构
		sysInforString=sysInforString + "账户名称：" + System.getProperty("user.name")+"\n";// user.name 用户的账户名称
		sysInforString=sysInforString + "jre版本：" + System.getProperty("java.version")+"\n";// 环境版本号
		sysInforString=sysInforString + "最大内存：" + Runtime.getRuntime().maxMemory()/1024/1024+"M\n";
		sysInforString=sysInforString + "可用内存：" + Runtime.getRuntime().totalMemory()/1024/1024+"M\n";
		sysInforString=sysInforString + "剩余内存：" + Runtime.getRuntime().freeMemory()/1024/1024+"M\n";
		sysInforString +=     "------------------------------------------\n";
		sysInforString=sysInforString + "设置参数...\n";
		setSysteInfor();
		sysInforString=sysInforString +"设定待发订单检测时间间隔" + systemInfor.getSeparatedTime() + "分钟\n";
		sysInforString=sysInforString +"设定待审商品检测时间间隔" + systemInfor.getPendEffectiveTime() + "分钟\n";
		sysInforString=sysInforString +"设定待审店铺检测时间间隔" + systemInfor.getShopPendingTime() + "分钟\n";
		sysInforString=sysInforString +"设定核心检测时间间隔" + systemInfor.getCoreTime() + "分钟\n";
		sysInforString=sysInforString +"设定参数对象可用" + systemInfor.getStatus()+ "分钟\n";
		sysInforString=sysInforString + "设置参数完毕\n";
		log.info(sysInforString);
	}
	public String ShowSystemInfor(){
		return ("\n" + gwfUtils.fileReadSelect("static/about.txt"));

	}
	public void setSysteInfor(){
		synchronized(systemInfor) {
			systemInfor.setSeparatedTime("60");
			systemInfor.setPendEffectiveTime("60");
			systemInfor.setShopPendingTime("60");
			systemInfor.setCoreTime("60");
			systemInfor.setStatus(true);
			systemInfor.notifyAll();
		}
	}

}
