package com.gwf.work.mode.modeImpl;

import com.gwf.work.mode.SeleniumHtmlCookie;
import com.gwf.work.utils.GwfUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * @author gwf
 * @version 1.0
 * @date 2021/4/16 9:12
 */
@Repository
public class SeleniumHtmlCookieImpl implements SeleniumHtmlCookie {
    private static final Logger log = LoggerFactory.getLogger(SeleniumHtmlCookie.class);
    @Autowired
    private GwfUtils gwfUtils;

    public String getHtmlCookie() {
        System.setProperty("webdriver.chrome.driver", gwfUtils.getFilePath("static/chromedriver.exe"));
        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(50, TimeUnit.SECONDS);   //设置默认超时时间
        //第一次登录
        driver.get("https://www.tf0914.com/login");
        driver.findElement(By.id("__BVID__3")).sendKeys("18607518849");
        driver.findElement(By.id("pwd")).sendKeys("chao000000.");
        driver.findElement(By.id("loginphone")).click();
        //刷新第二次登录
        driver.navigate().refresh();
        driver.get("https://www.tf0914.com/login");
        driver.findElement(By.id("__BVID__3")).sendKeys("18607518849");
        driver.findElement(By.id("pwd")).sendKeys("chao000000.");
        driver.findElement(By.id("loginphone")).click();
        //cookie格式化
        String cookies = "JSESSIONID=" + driver.manage().getCookieNamed("JSESSIONID").getValue()
                + "; sessionUser=" + driver.manage().getCookieNamed("sessionUser").getValue() + ";";
        driver.quit();
        return cookies;
    }

}
