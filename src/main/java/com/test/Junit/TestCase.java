package com.test.Junit;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

public class TestCase {
    private static ChromeDriver driver;
    /**
     * 测试之前先进行驱动浏览器启动
     */
    @BeforeTest
    public void before(){
        System.setProperty("webdriver.chrome.driver","D:\\XXZL\\笔记\\selenum\\chromedriver.exe");
        driver= new ChromeDriver();

    }

    /**
     * 每个测试用例一个步骤
     */
    @Test(dataProvider = "data")
    public void testa(String s1,String s2) throws InterruptedException {
         driver.get("http://192.168.16.124:6002/rod_cas_centerweb/login");
         Thread.sleep(5000);
         driver.findElementByName("username").sendKeys(s1);
         driver.findElementByName("password").sendKeys(s2);
         driver.findElementByClassName("btn-login").click();
        System.out.println(s1+":"+s2);
    }

    @DataProvider(name="data")
    public Object[][] providerData(){
       Object[][] obj=new Object[][]{
            {"system",""},
            {"","12345678"},
            {"",""}
        };
       return obj;
    }

    @AfterTest//测试之后退出浏览器
    public void after(){
          driver.quit();
    }
}
