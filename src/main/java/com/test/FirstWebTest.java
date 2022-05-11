package com.test;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;


public class FirstWebTest {
    public static void main(String[] args) {
       openFirefox();
        //   openChrome();
    }
//打开火狐浏览器
    public static void openFirefox(){
      //  FirefoxOptions optinos=new FirefoxOptions();
      //  optinos.setBinary("D:\\Program Files\\firfox\\firefox.exe");
        System.setProperty("webdriver.firefox.bin","D:\\Program Files\\firfox\\firefox.exe");
        System.setProperty("webdriver.gecko.driver","D:\\XXZL\\笔记\\selenum\\geckodriver.exe");
        //驱动
        FirefoxDriver firefoxDriver=new FirefoxDriver();
        firefoxDriver.get("http://www.baidu.com");
    }

    public static void openChrome(){
        System.setProperty("webdriver.chrome.driver","D:\\XXZL\\笔记\\selenum\\chromedriver.exe");
        ChromeDriver chromedriver= new ChromeDriver();
        chromedriver.get("http://www.baidu.com");
    }

}
