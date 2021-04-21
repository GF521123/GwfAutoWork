package com.gwf.work.utils.utilsImpl;

/**
 * @author GWF
 *
 */
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.Calendar;
import org.apache.logging.log4j.core.config.plugins.util.ResolverUtil.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.gwf.work.utils.GwfUtils;

@Repository("GwfUtils")
public class GwfUtilsImpls implements GwfUtils {
	private static final Logger log = LoggerFactory.getLogger(GwfUtilsImpls.class);

	@Override
	public String getFilePath(String fileName){
		File file = new File(Test.class.getClassLoader().getResource(fileName).getPath());
		if (file.exists()) {
			return Test.class.getClassLoader().getResource(fileName).getPath();
		} else {
//			return ClassPathResource("classpath:" + fileName);
			return "C:\\Program Files\\Google\\Chrome\\Application\\chromedriver.exe";
		}

	}

	@Override
	public String fileReadSelect(String fileName) {
		File file = new File(Test.class.getClassLoader().getResource(fileName).getPath());
		if (file.exists()) {
			return readCokies(fileName);
		} else {
			return readFileOnce(fileName);
		}
	}

//	文件操作
	@Override
	public String readCokies(String fileName) {
		try {
			String path = Test.class.getClassLoader().getResource(fileName).getPath();
			FileInputStream fis = new FileInputStream(path);
			BufferedInputStream bis = new BufferedInputStream(fis);
			String content = "";
			// 自定义缓冲区
			byte[] buffer = new byte[10240];
			int flag = 0;
			while ((flag = bis.read(buffer)) != -1) {
				content += new String(buffer, 0, flag, "utf-8");
			}
			// 关闭的时候只需要关闭最外层的流就行了
			bis.close();
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 一次性读取全部文件数据
	 *
	 * @param strFile
	 */
	@Override
	public String readFileOnce(String strFile) {
		String resStr = "";
		try {
			InputStream is = new ClassPathResource("classpath:" + strFile).getInputStream();
			int iAvail = is.available();
			byte[] bytes = new byte[iAvail];
			is.read(bytes);
//            logger.info("文件内容:\n" + new String(bytes));
			resStr += new String(bytes, "utf-8");
			is.close();
			return resStr;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 按行读取文件
	 *
	 * @param strFile
	 */
	@Override
	public String readFileByLine(String strFile) {
		String resString = "";
		try {
			File file = new File(strFile);
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String strLine = null;
			int lineCount = 1;
			while (null != (strLine = bufferedReader.readLine())) {
				resString += strLine;
				lineCount++;
			}
			return resString;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/*
	 *
	 */
	public String getObjectValueByName(Object object,String objectName){
		Class<?> aClass = object.getClass();
		//得到属性
		Field field = null;
		try {
			field = aClass.getDeclaredField(objectName);
			//打开私有访问
			field.setAccessible(true);
			//获取属性
			String name = field.getName();
			//获取属性值
			return (String) field.get(object);
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return "--";
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return "--";
		}
	}

	//************************************************************
	public boolean isRunTime(){
		Calendar cal = Calendar.getInstance();
		int hour=cal.get(Calendar.HOUR);//获取日
		log.info(hour+"");
		if (hour >= 8 && hour <= 18) {
			return true;
		}
		log.info("不在检索时间范围（6~18）内.....暂停检索");
		return false;
	}
}
