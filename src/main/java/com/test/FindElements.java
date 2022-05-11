package com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

public class FindElements {
    private static ChromeDriver chromeDriver;
    public static void main(String[] args) {
         openChrome();
         //定位byid搜索框元素
       // chromeDriver.findElement(By.id("kw")).sendKeys("腾讯课堂");
        //根据name定位
       // chromeDriver.findElementByName("wd").sendKeys("测试");
        //根据tagname,报错，找到第一个不可见的tagname，不推荐使用。
       // chromeDriver.findElement(By.tagName("input")).sendKeys("tagname");
        //通过className
       // chromeDriver.findElement(By.className("s_ipt")).sendKeys("试试");
        //对按钮操作，classname多个的只需写一个，并且确保这一个在页面没有重复
        //chromeDriver.findElementByClassName("s_btn").click();
       // chromeDriver.findElement(By.linkText("新闻")).click();
        //通过partiallinktest，超链接文本中的部分内容去定位元素
       // chromeDriver.findElementByPartialLinkText("图").click();
        //通过cssSelector定位
        //chromeDriver.findElement(By.cssSelector("#kw")).sendKeys("css");
        //chromeDriver.findElement(By.cssSelector("input#kw")).sendKeys("css");
      //  chromeDriver.findElement(By.cssSelector("input.s_ipt")).sendKeys("cssss");
       // chromeDriver.findElement(By.cssSelector("input[name='wd'][maxlength='255']")).sendKeys("ceshi");
        // chromeDriver.findElementByXPath("//*[@aria-label='百度热搜']").click();
    }

    public static void openChrome(){
        System.setProperty("webdriver.chrome.driver","D:\\XXZL\\笔记\\selenum\\chromedriver.exe");
        chromeDriver =new ChromeDriver();
        chromeDriver.get("http://www.baidu.com");
    }
}
