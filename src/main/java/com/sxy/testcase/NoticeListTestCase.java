package com.sxy.testcase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.sxy.utils.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.*;
import java.util.*;

/**
 * 通知公告列表查询
 */
public class NoticeListTestCase extends BaseTestCase{
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    //登录接口数据源
    @DataProvider(name = "loginData")
    public Object[][] getLoginData(){
        objectArray = ExcelUtil.getExcelByRelyCasesData(filePath, dataProviderSheetName,"登录");
        return objectArray;
    }

    @Test(description = "登录",dataProvider = "loginData")
    public void loginTest(String caseID, String caseName, String apiName, String apiUrl, String function, String parameter, String expRestult, String actResult, String executeTime, String testResult, String comment, String isRely, String isRun,String checkSql) {
        LoggerUtil.startTestCase(caseID, caseName);
        String url =domainUrl+apiUrl;
        int currentRow = ExcelUtil.getRowByCaseId(filePath, dataProviderSheetName, caseID);

        JSONObject jsonParm = JSONObject.parseObject(parameter);
        JSONObject jsonExpResult = JSONObject.parseObject(expRestult);
        JSONObject jsonActResult =  task.sendRequest(url, function, jsonParm);
        LoggerUtil.info("返回的jsonResult结果为：" + jsonActResult.toJSONString());
        token = jsonActResult.getString("token");
        LoggerUtil.info("获取到的token值：" + token);
        authorization = "Bearer "+token;

        jsonActResult.remove("token");
        // 校验
        CompareResultUtil.verificationResponseResult(filePath, dataProviderSheetName, currentRow, jsonActResult, jsonExpResult, currentRow, caseID, caseName);

        LoggerUtil.endTestCase(caseID, caseName);
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
       // LoggerUtil.info("返回的jsonResult结果为："+jsonActResult.toJSONString());

        //校验
        CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currentRow,jsonActResult,jsonExpResult,currentRow,caseID,caseName);
        LoggerUtil.endTestCase(caseID,caseName);
    }

    @DataProvider(name = "noticeListData")
    public Object[][] getNoticeListData(){
        objectArray = ExcelUtil.getExcelCasesData(filePath,dataProviderSheetName,"通知公告-查询",runIsRun);
        return objectArray;
    }

    @Test(description = "查询通知公告列表数据",dataProvider = "noticeListData",dependsOnMethods = "loginTest")
    public void searchNoticeListTest(String caseID, String caseName, String apiName, String apiUrl, String function, String parameter,
                                     String expRestult, String actResult, String executeTime, String testResult, String comment,
                                     String isRely, String isRun,String checkSql){
        LoggerUtil.startTestCase(caseID,caseName);

        int currentRow = ExcelUtil.getRowByCaseId(filePath,dataProviderSheetName,caseID);

        String url = domainUrl+apiUrl;
        //没有参数
        JSONObject jsonData = new JSONObject();

        JSONObject jsonExpResult = JSONObject.parseObject(expRestult);
        JSONObject jsonActResult = task.sendRequest(url,function,jsonData,authorization);
        LoggerUtil.info("=============返回的jsonResult结果为："+jsonActResult.toJSONString());

        String total = jsonActResult.getString("total");
        JSONArray rowsArray = jsonActResult.getJSONArray("rows");
        for (int i = 0; i < rowsArray.size(); i++) {
           JSONObject obj = rowsArray.getJSONObject(i);
            obj.remove("dataScope");
            obj.remove("remark");
            obj.remove("updateTime");
            obj.remove("searchValue");
        }

        //将实际返回的结果数据拼成josn对象
            JSONObject jsonObjectResData = new JSONObject();
            jsonObjectResData.put("total", total);
            jsonObjectResData.put("rows", rowsArray);//将数量和行数据放入json对象
            LoggerUtil.info("===================jsonObjectResData:" + jsonObjectResData);

            jsonActResult.remove("total");
            jsonActResult.remove("rows");

            //将用例中的检查sql语句存储成字符串
            String[] sqls = checkSql.split("##");
            String sql1 = sqls[0];
            String sql2 = sqls[1];
            Long totalDb = template.queryForObject(sql1, Long.class);// 将结果集封装为对象.一般查询总数或者聚合函数的

            List<Map<String, Object>> rowsInfoList = template.queryForList(sql2);//查询结果将结果集封装为List结果集

            for (Map<String, Object> stringObjectMap : rowsInfoList) {
                stringObjectMap.put("params", new JSONObject());//查询出的语句缺少params主键，所以添加{}空的json数据
            }
        //  LoggerUtil.info("rowsInfoList----------------------:" + rowsInfoList);

            //将list转化为JSONArray
          JSONArray array = JSONArray.parseArray(JSON.toJSONString(rowsInfoList));


            //将数据库查的的条数和返回的数据组成一个返回数据结果集
            JSONObject jsonObjectDb = new JSONObject();
            jsonObjectDb.put("total", String.valueOf(totalDb));//将获取的总数改完string类型
            jsonObjectDb.put("rows", array);
            LoggerUtil.info("jsonObjectDb:" + jsonObjectDb);


          Assert.assertEquals(jsonObjectResData, jsonObjectDb);
        LoggerUtil.endTestCase(caseID,caseName);

      //  CompareResultUtil.verificationResponseResult(filePath,dataProviderSheetName,currentRow,jsonActResult,jsonExpResult,
      //          currentRow,caseID,caseName);
    }



    @Test(description = "将string中顺序不一致的json字符串转化为json")
    public void testOrder(){
        String str1 ="{\'updateTime\':\'2018-03-16 11:33:00\',\'remark\':\'管理员\'}";
        LinkedHashMap<String, Object> jsonMap1 = JSON.parseObject(str1, LinkedHashMap.class, Feature.OrderedField);
        JSONObject jsonObject1 = new JSONObject(true);
        jsonObject1.putAll(jsonMap1);
        System.out.println(jsonObject1.toJSONString());

        String str2 ="{remark:管理员,updateTime:2018-03-16 11:33:00}";
        LinkedHashMap<String, Object> jsonMap2 = JSON.parseObject(str1,LinkedHashMap.class, Feature.OrderedField);
        JSONObject jsonObject2 = new JSONObject(true);
        jsonObject2.putAll(jsonMap2);
        System.out.println(jsonObject2.toString());

        Assert.assertEquals(jsonObject1.toString(),jsonObject2.toString());
    }

    @Test(description = "将string中顺序不一致的json字符串转化为json")
    public void test1(){
        String str1 ="{\"noticeType\":\"1\",\"noticeId\":2,\"noticeTitle\":\"公告20220422173829\",\"noticeContent\":\"通知放假时间为1-3号\",\"createBy\":\"admin\",\"createTime\":\"2022-04-22 17:38:18\",\"updateBy\":\"\",\"status\":\"0\"}";
        JSONArray array1 = new JSONArray();
        array1.add(JSONObject.parseObject(str1));
        System.out.println("array1:"+array1);
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("rows",array1);
        jsonObject1.put("num","1");
        System.out.println(jsonObject1.toString());

        String str2 ="{\"noticeContent\":\"通知放假时间为1-3号\",\"createBy\":\"admin\",\"createTime\":\"2022-04-22 17:38:18\",\"updateBy\":\"\",\"noticeType\":\"1\",\"noticeId\":2,\"noticeTitle\":\"公告20220422173829\",\"status\":\"0\"}";
        JSONArray array2 = new JSONArray();
        array2.add(JSONObject.parseObject(str2));
        System.out.println("array2:"+array2.toString());
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("rows",array2);
        jsonObject2.put("num","1");
        System.out.println(jsonObject2.toString());

        Assert.assertEquals(array1,array2);//true
      //  Assert.assertEquals(jsonObject1.toString(),jsonObject2.toString());//false
     //   Assert.assertEquals(jsonObject1,jsonObject2);//true
    }

}
