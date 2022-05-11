package com.sxy.testcase;

import com.alibaba.fastjson.JSONObject;
import com.sxy.utils.CompareResultUtil;
import com.sxy.utils.ExcelUtil;
import com.sxy.utils.LoggerUtil;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MyinfoTestCase extends  BaseTestCase{
    //登录接口数据源
    @DataProvider(name="loginData")
    public Object[][] getLoginData(){
        objectArray = ExcelUtil.getExcelByRelyCasesData(filePath,dataProviderSheetName,"登录");
        return objectArray;
    }

    @Test(description ="登录",dataProvider = "loginData")
    public void loginTest(String caseID, String caseName, String apiName, String apiUrl, String function,
                          String parameter, String expRestult, String actResult, String executeTime,
                          String testResult, String comment, String isRely, String isRun,String checkSql){

        LoggerUtil.startTestCase(caseID,caseName);
        String url = domainUrl+apiUrl;//接口地址
        int currenRow = ExcelUtil.getRowByCaseId(filePath,dataProviderSheetName,caseID);//获取用例行
//将参数、实际结果、预期结果格式化
        JSONObject jsonParm = JSONObject.parseObject(parameter);
        JSONObject jsonExpResult = JSONObject.parseObject(expRestult);
        JSONObject jsonActResult = task.sendRequest(url,function,jsonParm);

        LoggerUtil.info("返回的jsonResult结果为："+jsonActResult.toJSONString());
        token = jsonActResult.getString("token");
        LoggerUtil.info("获取到的token值："+token);
        jsonActResult.remove("token");//实际结果中将token内容移除

        authorization = "Bearer "+token;

        //校验
        CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currenRow,jsonActResult,
                jsonExpResult,currenRow,caseID,caseName);

        LoggerUtil.endTestCase(caseID,caseName);
    }

    //我的个人信息数据源
    @DataProvider(name = "myinfoData")
    public Object[][] getClaimList(){
        objectArray = ExcelUtil.getExcelCasesData(filePath,dataProviderSheetName,"查看个人信息",runIsRun);
        return objectArray;
    }
    //依赖上面的方法才能执行
    @Test(description="查看我的信息测试",dataProvider = "myinfoData",dependsOnMethods = "loginTest")
    public void myinfoTest(String caseID, String caseName, String apiName, String apiUrl, String function,
                           String parameter, String expRestult, String actResult, String executeTime,
                           String testResult, String comment, String isRely, String isRun,String checkSql){
        LoggerUtil.startTestCase(caseID,caseName);
          String url = domainUrl+apiUrl;
          int currentRow = ExcelUtil.getRowByCaseId(filePath,dataProviderSheetName,caseID);
          //没有参数，创建个空的数据
          JSONObject jsonData = new JSONObject();

          JSONObject jsonExpResult = JSONObject.parseObject(expRestult);
          JSONObject jsonActResult = task.sendRequest(url,function,jsonData,authorization);
          LoggerUtil.info("返回的jsonResult结果为："+jsonActResult.toJSONString());
          //返回结果为msg,postGroup,code,data，roleGroup，个人数据都不一样，只能比对msg和code成功，所以实际结果移除这两项
          jsonActResult.remove("postGroup");
          jsonActResult.remove("data");
          jsonActResult.remove("roleGroup");

          //校验
          CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currentRow,jsonActResult,jsonExpResult,currentRow,caseID,caseName);
          LoggerUtil.endTestCase(caseID,caseName);



    }
}
