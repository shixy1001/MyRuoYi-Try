package com.test;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.w3c.dom.html.HTMLImageElement;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ElementsAPI {
    private static ChromeDriver chromeDriver;
    public static void main(String[] args) throws InterruptedException {
        openChrome();
        loginTest();
        Thread.sleep(8000);
        WebDriverAPI();
//        navAPI();

    }
    public static void openChrome(){
        System.setProperty("webdriver.chrome.driver","D:\\XXZL\\笔记\\selenum\\chromedriver.exe");
        chromeDriver =new ChromeDriver();
        chromeDriver.get("http://192.168.16.37:6002/rod_cas_centerweb/index");
        chromeDriver.get("http://192.168.16.37:6002/rod_cas_centerweb/index");
    }
    public static void ElementAPI() throws InterruptedException {
        WebElement wb=chromeDriver.findElement(By.name("username"));
        wb.sendKeys("system");
        Thread.sleep(3000);//停留3s
        wb.clear();
        String s1=wb.getTagName();
        System.out.println("username的tagname是"+s1);
        String s2=wb.getText();
        System.out.println("username的text是"+s2);
        String s3=wb.getAttribute("class");
        System.out.println("username的class属性是"+s3);
        Boolean b=wb.isDisplayed();
        System.out.println(b);
    }

    public static void WebDriverAPI() throws InterruptedException {
        System.out.println(chromeDriver.getTitle());
        Thread.sleep(3000);
        chromeDriver.findElement(By.id("firstPartMenu")).findElement(By.linkText("认证系统管理")).click();
       Thread.sleep(3000);

        //定义webdriverwait对象
      WebDriverWait wdw = new WebDriverWait(chromeDriver,5);

        //webdriverwait对象.util对元素进行判断By找元素
        //元素存在及操作
        wdw.until(ExpectedConditions.presenceOfElementLocated(By.id("layout_west_tree")));
        chromeDriver.findElementByXPath("//div[@class='leftCon']/ul/li/div").click();//点二级菜单
     //点三级菜单
        Thread.sleep(3000);
        chromeDriver.findElement(By.xpath("//div[@class='leftCon']/ul/li/ul/li/div")).click();
        System.out.println("点三级菜单");
        Thread.sleep(3000);
        //页面最大化
        chromeDriver.manage().window().maximize();
      //页面html找那个有iframe下还有html嵌入的表格，需要先找到iframe这个元素
        WebElement wb =chromeDriver.findElementByXPath("//div[@id='control']/div/div[2]/div[2]//iframe");
       chromeDriver.switchTo().frame(wb);//swichto从原有html跳转到嵌入的iframe，从这里de html重新开始找元素
        //查看查询条件下拉框
        chromeDriver.findElementByXPath("//form[@id='searchForm']/div/div/div/div/div[3]/span/span/a").click();
        System.out.println("点窗口打开下拉");
        Select sel=new Select(chromeDriver.findElementByXPath("//body/div[4]/div[1]"));
        List<WebElement> list=sel.getOptions();
        for (WebElement w:list) {
            System.out.println(w);
        }
//        //d点击系统链接
//       chromeDriver.findElementByClassName("panel-fit").findElement(By.linkText("http://192.168.16.39:6110/uws-schoolroll/")).click();
//       Thread.sleep(3000);
//      chromeDriver.switchTo().window(chromeDriver.getWindowHandle());
       //chromeDriver.close();//关闭操作的主页，新打开的链接页不关
//        System.out.println("handle"+chromeDriver.getWindowHandle());
//        System.out.println("handles"+chromeDriver.getWindowHandles());
//        WebDriver.Options op= chromeDriver.manage();
//        System.out.println(op);
//        System.out.println(op.getCookies());
//        System.out.println(op.getClass());
//        System.out.println(op.window());
//        System.out.println(op.window().getPosition());
//        System.out.println(op.window().getSize());
//        op.window().maximize();
       // System.out.println(op.window().);


    }
    public static void loginTest(){
        chromeDriver.findElement(By.name("username")).sendKeys("system");
        chromeDriver.findElement(By.name("password")).sendKeys("uwaysoft123");
        chromeDriver.findElement(By.xpath("//*[@onclick='submitForm()']")).click();
    }

    public static void navAPI() throws InterruptedException {
        //隐式等待，针对全局，在2S内找到元素就操作，否则超时，不必每步都设置等待
        chromeDriver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        WebDriver.Navigation ng=chromeDriver.navigate();
        ng.to("http://www.baidu.com");
       // Thread.sleep(2000);//硬性等待，针对当前当前操作有效
        ng.refresh();
       // Thread.sleep(2000);
        ng.back();
      //  Thread.sleep(2000);
        ng.forward();
      //  Thread.sleep(2000);
        chromeDriver.quit();
    }
}
