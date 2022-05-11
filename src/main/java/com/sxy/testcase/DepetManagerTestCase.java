package com.sxy.testcase;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxy.utils.CompareResultUtil;
import com.sxy.utils.ExcelUtil;
import com.sxy.utils.JDBCUtils;
import com.sxy.utils.LoggerUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import java.util.*;

public class DepetManagerTestCase extends BaseTestCase{
    private  JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    public String deptid;//新增部门的id号
    int id;//新增部门的表中dept_id号

    @DataProvider(name = "log")
    public Object[][] getLoginData(){
        objectArray= ExcelUtil.getExcelCasesData(filePath,dataProviderSheetName,"登录",runIsRun);
        return objectArray;
    }
    @Test(description = "登录",dataProvider = "log")
    public void logT(String caseID,String caseName,String apiName,String apiUrl,String function,String parameter,
                          String expResult,String actResult,String excuteTime,String testResult,String comment,
                          String isRely,String isRun,String checkSql){
        LoggerUtil.startTestCase(caseID,caseName);
        int currentRow = ExcelUtil.getRowByCaseId(filePath,dataProviderSheetName,caseID);
        //发送请求，获取响应
        String url = domainUrl+apiUrl;
        JSONObject jsonParm = JSONObject.parseObject(parameter);
        JSONObject jsonExp = JSONObject.parseObject(expResult);
        JSONObject jsonAct = task.sendRequest(url,function,jsonParm);
        LoggerUtil.info("返回的jsonResult结果为："+jsonAct.toJSONString());
        //返回值的token转换为authorization
        token = jsonAct.getString("token");
        authorization = "Bearer "+token;

        jsonAct.remove("token");
        CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currentRow,jsonAct,jsonExp,currentRow,caseID,caseName);

        LoggerUtil.endTestCase(caseID,caseName);
    }

    @DataProvider(name = "addDept")
    public Object[][] getDeptAddData(){
        objectArray = ExcelUtil.getExcelCasesData(filePath,dataProviderSheetName,"部门管理-新增",runIsRun);
        return objectArray;
    }
    @Test(description = "新增部门",dataProvider = "addDept",dependsOnMethods = "logT")
    public void deptAddTest(String caseID,String caseName,String apiName,String apiUrl,String function,String parameter,
                            String expResult,String actResult,String excuteTime,String testResult,String comment,
                            String isRely,String isRun,String checkSql){
         LoggerUtil.startTestCase(caseID,caseName);
         int currentRow = ExcelUtil.getRowByCaseId(filePath,dataProviderSheetName,caseID);

         String url = domainUrl+apiUrl;
        String pam=null;
         if(parameter.contains("#id")){
            int t = (Integer)template.queryForObject("select max(dept_id) from sys_dept",java.lang.Integer.class);
            id = t+1;
            pam = parameter.replace("#id",String.valueOf(id));
         }
         JSONObject jsonParm = JSONObject.parseObject(pam);
         JSONObject jsonExp = JSONObject.parseObject(expResult);
         JSONObject jsonAct = task.sendRequest(url,function,jsonParm,authorization);
         LoggerUtil.info("实际返回响应为："+jsonAct.toJSONString());


         CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currentRow,jsonAct,jsonExp,currentRow,caseID,caseName);
         LoggerUtil.endTestCase(caseID,caseName);
    }

    @DataProvider(name = "queryDept")
    public Object[][] getDeptQueryData(){
        objectArray = ExcelUtil.getExcelCasesData(filePath,dataProviderSheetName,"部门管理-查询",runIsRun);
        return objectArray;
    }
    @Test(description = "查询部门",dataProvider = "queryDept",dependsOnMethods = "logT")
    public void queryDept(String caseID,String caseName,String apiName,String apiUrl,String function,String parameter,
                            String expResult,String actResult,String excuteTime,String testResult,String comment,
                            String isRely,String isRun,String checkSql){
        LoggerUtil.startTestCase(caseID,caseName);
        int currentRow = ExcelUtil.getRowByCaseId(filePath,dataProviderSheetName,caseID);

        String url = domainUrl+apiUrl;
        JSONObject jsonParm = new JSONObject();//get请求没有参数，给{}空对象做参数
        JSONObject jsonExp = JSONObject.parseObject(expResult);
        JSONObject jsonAct = task.sendRequest(url,function,jsonParm,authorization);
        LoggerUtil.info("实际返回响应为："+jsonAct.toJSONString());

//        //获取新增部门的deptid号
//        String data = jsonAct.getString("data");
//        JSONArray array = JSONArray.parseArray(data);
//        JSONObject jsonObjDetp;
//        String name = "Test"+Integer.toString(id);
//        for (int i = 0; i < array.size(); i++) {//遍历部门对象数组，找到新增的部门对象
//            jsonObjDetp = array.getJSONObject(i);//取得对象
//            if(jsonObjDetp.getString("deptName").equals("name")){
//                deptid = jsonObjDetp.getString("deptId");
//                break;
//            }
//        }

         jsonAct.remove("data");
        CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currentRow,jsonAct,jsonExp,currentRow,caseID,caseName);
        LoggerUtil.endTestCase(caseID,caseName);
    }

    @DataProvider(name = "delDept")
    public Object[][] deleteDeptData(){
        objectArray = ExcelUtil.getExcelCasesData(filePath,dataProviderSheetName,"部门管理-删除",runIsRun);
        return objectArray;
    }
    @Test(description = "删除部门",dataProvider = "delDept",dependsOnMethods = {"logT","deptAddTest"})
    public void delDeptT(String caseID,String caseName,String apiName,String apiUrl,String function,String parameter,
                             String expResult,String actResult,String excuteTime,String testResult,String comment,
                             String isRely,String isRun,String checkSql){
        LoggerUtil.startTestCase(caseID,caseName);
        int currentRow = ExcelUtil.getRowByCaseId(filePath,dataProviderSheetName,caseID);

       String au = apiUrl.replace("${deptid}",Integer.toString(id));
       String url = domainUrl + au;

       JSONObject jsonExp = JSONObject.parseObject(expResult);
       JSONObject jsonParm = new JSONObject();
       JSONObject jsonAct = task.sendRequest(url,function,jsonParm,authorization);
       LoggerUtil.info("请求响应结果为："+jsonAct.toJSONString());


        CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currentRow,jsonAct,jsonExp,currentRow,caseID,caseName);
        LoggerUtil.endTestCase(caseID,caseName);
    }
}
