package com.sxy.testcase;

import com.sxy.task.HttpClientTask;
import com.sxy.utils.CommonUtil;
import org.testng.annotations.BeforeClass;

public class BaseTestCase {
    public HttpClientTask task;
    public String filePath;
    public String dataProviderSheetName;
    public String domainUrl;
    public String runIsRun;
    public String token;
    public String authorization;
    public Object[][] objectArray;
    public String uuid;

    @BeforeClass(description = "初始化读取文件和配置")
    public void beforeTest(){
        //拿到用例路径,默认是从此类所在的包下取资源,以’/'开头时，则是从项目的ClassPath根下获取资源。
        filePath = this.getClass().getResource("/TestCases.xlsx").getPath();
      //  System.out.println("filePath:"+filePath);
        //sheet文件名
        dataProviderSheetName = "RuoYi";

        //读取配置文件中的key值
        domainUrl = CommonUtil.getPropertiesInfo("runsetting","domain.url");
        runIsRun = CommonUtil.getPropertiesInfo("runsetting","run.isRun");

        task =new HttpClientTask();

    }
}
