package com.gwf.work.utils;

import org.springframework.stereotype.Repository;

/**
 * @author GWF
 *
 */


public interface GwfUtils {
	/*
	 * 获取文件路径
	 *
	 */
	public String getFilePath(String fileName);

	public String fileReadSelect(String fileName);

	public String readFileOnce(String strFile);

	public String readFileByLine(String strFile);

	String readCokies(String fileName);
	/*
	 * @根据对象，字段名获取值
	 * @param object 类
	 * @param objectName 字段名
	 * 未使用
	 */
	public String getObjectValueByName(Object object,String objectName);
	/*
	 * 判读是否自动工作时间
	 */
	public boolean isRunTime();


	public void ConsoleHead(String name);

	public void ConsoleEnd(String name);
}
