package com.test.XGImport;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SponerImport {
    //两个驱动根据使用哪个浏览器调用拿一个
    private static ChromeDriver driver;
 //   private static FirefoxDriver driver;


    public static void main(String[] args) throws InterruptedException {
        //调用驱动打开网页
        String URL="http://192.168.16.122:5517/uws-stu/";//定义访问地址
        openChrome(URL);//调用浏览器访问
        Login();//登录
        Boolean res=grantImport();//助学金导入
        if(res==true){
            System.out.println("导入成功");
            driver.quit();
        }else
            System.out.println("导入失败，请查找原因");

    }


    //谷歌浏览器的接口
    public  static void openChrome(String URL){
        System.setProperty("webdriver.chrome.driver","D:\\XXZL\\笔记\\selenum\\chromedriver.exe");
        driver= new ChromeDriver();
        driver.get(URL);
    }
    //火狐浏览器接口
    public  static void openFireFox(String URL){
        System.setProperty("webdriver.gecko.driver","D:\\XXZL\\笔记\\selenum\\geckodriver.exe");
        System.setProperty("webdriver.firefox.bin","D:\\Program Files\\firfox\\firefox.exe");
        FirefoxDriver driver= new FirefoxDriver();
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

    public static Boolean grantImport() throws InterruptedException {
        //设置驱动等待对象
        WebDriverWait wdw=new WebDriverWait(driver,3);
        //点击资助管理菜单
        WebElement zzglmenu=wdw.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='left_menu']//a[@data-id='4028933c549e9fb501549ea28a450001']")));
        zzglmenu.click();
        //点击导航图上助学金导入图标
        WebElement image=wdw.until(ExpectedConditions.elementToBeClickable(By.linkText("助学金录入")));
        image.click();
        //当页面元素存在时,点击导入按钮
        WebElement imb=wdw.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[@onclick='importGrantInfo()']")));
        imb.click();
        //选择文件并上传
        Thread.sleep(3000);
        driver.findElement(By.cssSelector("input[id='file']")).sendKeys("D:\\Documents\\Downloads\\import_grantInfo (4).xls");
        driver.manage().window().maximize();
        WebElement up=wdw.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//form[@id='fileUpload']/div[3]//button[@onclick='importGrantInfo()']"))));
        up.click();
       //出现导入中的提示窗口时，就一直等待
        while(driver.findElement(By.id("messagemodal")).isDisplayed()){
            Thread.sleep(1000);
        }
        //导入完毕，如果出现重复数据，点确定导入按钮
        if(driver.findElement(By.className("btnCenter")).isDisplayed()){
            driver.findElement(By.className("btnCenter")).click();//点击确定导入
//等确定导入的窗口出现可点击
            WebElement cf=wdw.until(ExpectedConditions.elementToBeClickable(By.id("myModal")));
            driver.findElement(By.id("myModal")).findElement(By.id("idconfirm")).click();//点确定按钮，进行导入操作

        }
        //如果没有提示重复，直接导入，等待出导入结果
        if((driver.findElement(By.xpath("//div[@id='dt_gal_wrapper']/span")).getText()).equals("导入成功")){
            return true;
        }else{
            return false;
        }
    }


}
