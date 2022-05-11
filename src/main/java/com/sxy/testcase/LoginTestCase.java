package com.sxy.testcase;

import com.alibaba.fastjson.JSONObject;
import com.sxy.utils.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class LoginTestCase extends BaseTestCase{

    @DataProvider(name ="Uuid")
    public Object[][] getUuid(){
        objectArray = ExcelUtil.getExcelByRelyCasesData(filePath,dataProviderSheetName,"获取uuid");
        return objectArray;
    }
    @Test(description = "获取uuid值",dataProvider = "Uuid")
    public void getUuid(String caseID,String caseName,String apiName,String apiUrl,String function,String parameter,
                        String expResult,String actResult,String executeTime,String testResult,
                        String comment,String isRely,String isRun,String checkSql){
        LoggerUtil.startTestCase(caseID,caseName);
        String url = domainUrl+apiUrl;
        int currentrow = ExcelUtil.getRowByCaseId(filePath,dataProviderSheetName,caseID);

        JSONObject jsonExpResult = JSONObject.parseObject(expResult);
        JSONObject jsonActResult;
        if(parameter.equals("/")){
            jsonActResult = task.sendRequest(url,function);
        }else {
            JSONObject jsonParam = JSONObject.parseObject(parameter);
            jsonActResult = task.sendRequest(url,function,jsonParam);
        }

        jsonActResult.remove("img");
        LoggerUtil.info("请求登录的返回结果为："+jsonActResult.toJSONString());

        uuid = jsonActResult.getString("uuid");
        LoggerUtil.info("取得的uuid是："+uuid);

        LoggerUtil.endTestCase(caseID,caseName);

    }

    @DataProvider(name="loginData")
    public Object[][] getLoginData(){
        objectArray= ExcelUtil.getExcelCasesData(filePath,dataProviderSheetName,"登录",runIsRun);
        return objectArray;
    }

    @Test(description = "登录测试",dataProvider = "loginData",dependsOnMethods = "getUuid")
    public void loginTest(String caseID,String caseName,String apiName,String apiUrl,String function,String parameter,
                          String expResult,String actResult,String executeTime,String testResult,
                          String comment,String isRely,String isRun,String checkSql){
        //输出开始用例名
        LoggerUtil.startTestCase(caseID,caseName);
        //访问地址
        String url = domainUrl+apiUrl;

        //根据caeid获取用例行号
        int currentRow = ExcelUtil.getRowByCaseId(filePath,dataProviderSheetName,caseID);

        //参数转为json格式变量
        if(parameter.contains("#uuid")){
            parameter = parameter.replace("#uuid",uuid);
        }
        JSONObject jsonParm = JSONObject.parseObject(parameter);
        //预期结果转成json格式
        JSONObject jsonExpResult =JSONObject.parseObject(expResult);
        //通过httpclienttask发送请求
       JSONObject jsonActResult = task.sendRequest(url,function,jsonParm);

        LoggerUtil.info("返回的jsonResult结果为："+jsonActResult.toJSONString());
        token = jsonActResult.getString("token");
        LoggerUtil.info("取到的token值为："+token);
    //    authorization = "Bearer "+token;
        //因为token随机变动，所以校验预期结果是否与实际结果需要去掉token部分
        jsonActResult.remove("token");
        //校验
        CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currentRow,jsonActResult,
                jsonExpResult,currentRow,caseID,caseName);
        LoggerUtil.endTestCase(caseID,caseName);

    }

    /**
    @Test
    public void test(){
        //System.out.println(CommonUtil.getPropertiesInfo("runsetting","domain.url"));
        //System.out.println(ResourceBundle.getBundle("runsetting").getString("domain.url"));
//      String s =CommonUtil.getPropertiesInfo("log4j","log4j.appender.A1");
//        System.out.println(s);
        File file = new File("E:\\测试项目\\01项目\\02海事\\俱乐部排课\\问题.txt");
        String s =CommonUtil.txt2String(file);
        System.out.println(s);
    }
     */
}
