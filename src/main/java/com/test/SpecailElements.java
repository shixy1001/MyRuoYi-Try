package com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class SpecailElements {
    //两个驱动根据使用哪个浏览器调用拿一个
    private static ChromeDriver driver;
    //   private static FirefoxDriver driver;


    public static void main(String[] args) throws InterruptedException {
        //调用驱动打开网页
        String URL="http://192.168.16.122:5517/uws-stu/";//定义访问地址
        openChrome(URL);//调用浏览器访问
        Login();//登录
        grantImport();

    }


    //谷歌浏览器的接口
    public  static void openChrome(String URL){
        System.setProperty("webdriver.chrome.driver","D:\\XXZL\\笔记\\selenum\\chromedriver.exe");
        driver= new ChromeDriver();
        driver.get(URL);
    }
   //登录
    public static void Login(){
        //定义等待对象，2s时长
        WebDriverWait wdw=new WebDriverWait(driver,2);
        //显性等待，用户名可见时操作
        WebElement username=wdw.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("username"))));
        username.sendKeys("system");
        WebElement password=wdw.until(ExpectedConditions.visibilityOf(driver.findElement(By.name("password"))));
        password.sendKeys("12345678");
        WebElement submit=wdw.until(ExpectedConditions.elementToBeClickable(By.className("loginbtn")));
        submit.click();
    }

    public static void grantImport() throws InterruptedException {
        //设置驱动等待对象
        WebDriverWait wdw=new WebDriverWait(driver,3);
        //点击部门档案菜单
        WebElement zzglmenu=wdw.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='left_menu']//a[@data-id='402893646f63dc9a016f63f92e340001']")));
        zzglmenu.click();
//        //点击导航图上助学金导入图标
        WebElement image=wdw.until(ExpectedConditions.elementToBeClickable(By.linkText("查询统计")));
        image.click();
        Thread.sleep(3000);
//直接输入日期
        driver.findElementById("startDate").sendKeys("2022-01-12");
//        //通过js操作控件
//        JavascriptExecutor jsExecutor =(JavascriptExecutor) driver;
//        jsExecutor.executeScript("2022-01-11");

    }
}
