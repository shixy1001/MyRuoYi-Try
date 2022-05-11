package com.sxy.testcase;

import com.alibaba.fastjson.JSONObject;
import com.sxy.utils.CommonUtil;
import com.sxy.utils.CompareResultUtil;
import com.sxy.utils.ExcelUtil;
import com.sxy.utils.LoggerUtil;
import org.testng.annotations.AfterClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class NoticeAddTestcase extends BaseTestCase{
    @DataProvider(name = "loginData")
    public Object[][] getLoginData(){
        objectArray = ExcelUtil.getExcelByRelyCasesData(filePath,dataProviderSheetName,"登录");
        return objectArray;
    }
    @Test(description = "登录",dataProvider = "loginData")
    public void loginTest(String caseID, String caseName, String apiName, String apiUrl, String function, String parameter, String expRestult, String actResult, String executeTime, String testResult, String comment, String isRely, String isRun,String checkSql){
        LoggerUtil.startTestCase(caseID,caseName);

        String url =domainUrl+apiUrl;
        int currentRow = ExcelUtil.getRowByCaseId(filePath, dataProviderSheetName, caseID);

        JSONObject jsonParm = JSONObject.parseObject(parameter);
        JSONObject jsonExpResult = JSONObject.parseObject(expRestult);
        JSONObject jsonActResult =  task.sendRequest(url, function, jsonParm);
        LoggerUtil.info("返回的jsonResult结果为：" + jsonActResult.toJSONString());

        token = jsonActResult.getString("token");
        authorization = "Bearer "+token;
        LoggerUtil.info("获取到的authorization值：" + authorization);
        jsonActResult.remove("token");

        CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currentRow,jsonActResult,jsonExpResult,currentRow,caseID,caseName);
        LoggerUtil.endTestCase(caseID,caseName);
    }

    @DataProvider(name = "noticeAddData")
    public Object[][] getNoticeAddData(){
        objectArray = ExcelUtil.getExcelCasesData(filePath,dataProviderSheetName,"通知公告-新增",runIsRun);
        return objectArray;
    }
    @Test(description = "新增通知公告",dataProvider = "noticeAddData",dependsOnMethods = "loginTest")
    public void noticeAddTest(String caseID, String caseName, String apiName, String apiUrl, String function, String parameter,
                              String expRestult, String actResult, String executeTime, String testResult, String comment, String isRely, String isRun,String checkSql){
        LoggerUtil.startTestCase(caseID,caseName);
        String url = domainUrl+apiUrl;
        int currentRow = ExcelUtil.getRowByCaseId(filePath,dataProviderSheetName,caseID);

        //将testcase用中的参数替换为时间
        String actParameter = null;
        if(parameter.contains("#date")){
            actParameter =parameter.replace("#date", CommonUtil.getCurrentSysTime());
        }

        JSONObject jsonParm = JSONObject.parseObject(actParameter);//实际参数转换json格式
        JSONObject jsonExpResult = JSONObject.parseObject(expRestult);
        //发送请求获取响应结果
        JSONObject jsonActResult = task.sendRequest(url,function,jsonParm,authorization);
        LoggerUtil.info("返回的jsonResult结果为："+jsonActResult.toJSONString());

        //校验
        CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currentRow,jsonActResult,jsonExpResult,currentRow,caseID,caseName);
        LoggerUtil.endTestCase(caseID,caseName);
    }
}
