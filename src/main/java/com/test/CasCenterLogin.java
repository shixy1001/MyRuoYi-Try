package com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class CasCenterLogin {
    private static ChromeDriver chromeDriver;
    public static void main(String[] args) {
        //打开浏览器并请求地址
        openChrome();
        //登录操作
        login();

    }

    public static void openChrome(){
        //设置驱动路径
        System.setProperty("webdriver.chrome.driver","D:\\XXZL\\笔记\\selenum\\chromedriver.exe");
        //初始化驱动对象
        chromeDriver= new ChromeDriver();
        //获取url请求
        chromeDriver.get("http://192.168.16.37:6002/rod_cas_centerweb/login");
    }

    public static void login(){
        //定位元素,输入用户名和密码
        chromeDriver.findElement(By.name("username")).sendKeys("system");
        chromeDriver.findElement(By.name("password")).sendKeys("uwaysoft123");
        //点击登录按钮
        chromeDriver.findElement(By.className("btn-login")).click();
    }


}
