package com.sxy.utils;

import com.alibaba.fastjson.JSONObject;

public class CompareResultUtil {

    public static int actResultNo = 7; //用例中实际结果在第7列
    public static int executeTimeNo = 8;
    public static int testResultNo = 9;
    public static int commenNo = 10;//用例中报错信息在第10列

    public static void verificationResponseResult(String filePath, String sheetName, int currenRow,
                                                  JSONObject jsonActResult,JSONObject jsonExpResult,
                                                  int row,String caseID,String caseName){
         if(Assertion.verifyEquals(jsonActResult.toString(),jsonExpResult.toString())){
               LoggerUtil.info("测试用例编号:"+caseID+";用例名称："+caseName+";执行成功！！");
               ExcelUtil.setCellData(filePath,sheetName,currenRow,testResultNo,"Passed",true);
               ExcelUtil.setCellData(filePath,sheetName,currenRow,executeTimeNo,CommonUtil.getCurrentTime(),true);
               ExcelUtil.setCellData(filePath,sheetName,currenRow,actResultNo,jsonActResult.toString(),true);
               ExcelUtil.setCellData(filePath,sheetName,currenRow,commenNo,"用例执行通过",true);

         }else {
             LoggerUtil.info("测试用例编号:"+caseID+";用例名称："+caseName+";执行失败！！");
             ExcelUtil.setCellData(filePath,sheetName,currenRow,testResultNo,"Failed",false);
             ExcelUtil.setCellData(filePath,sheetName,currenRow,executeTimeNo,CommonUtil.getCurrentTime(),false);
             ExcelUtil.setCellData(filePath,sheetName,currenRow,actResultNo,jsonActResult.toString(),false);
             ExcelUtil.setCellData(filePath,sheetName,currenRow,commenNo,"与预期不同",false);
         }
        row++;
    }

    public static void verificationResponseResult(String filePath, String sheetName, int currenRow,
                                                  JSONObject jsonActResult,JSONObject jsonExpResult,
                                                  int row,String caseID,String caseName,boolean flag,String jsonActResultAll){
        //实际与预期相同，&& flag为true
         if(Assertion.verifyEquals(jsonActResult.toString(),jsonExpResult.toString()) && Assertion.verifyTrue(flag)){
             LoggerUtil.info("测试用例编号:"+caseID+";用例名称："+caseName+";执行成功！！");
             ExcelUtil.setCellData(filePath,sheetName,currenRow,testResultNo,"Passed",true);
             ExcelUtil.setCellData(filePath,sheetName,currenRow,executeTimeNo,CommonUtil.getCurrentTime(),true);
             ExcelUtil.setCellData(filePath,sheetName,currenRow,actResultNo,jsonActResult.toString(),true);
             ExcelUtil.setCellData(filePath,sheetName,currenRow,commenNo,"用例执行通过",true);
         }else {
             //将原始的返回结果值jsonActResultAll回写excel
             LoggerUtil.info("测试用例编号:"+caseID+";用例名称："+caseName+";执行失败！！");
             ExcelUtil.setCellData(filePath,sheetName,currenRow,testResultNo,"Failed",false);
             ExcelUtil.setCellData(filePath,sheetName,currenRow,executeTimeNo,CommonUtil.getCurrentTime(),false);
             ExcelUtil.setCellData(filePath,sheetName,currenRow,actResultNo,jsonActResultAll.toString(),false);
             ExcelUtil.setCellData(filePath,sheetName,currenRow,commenNo,"与预期不同",false);
         }
         row++;
    }

    /**
     *
     * @param filePath/sheetName/currenRow/row/caseID/caseName
     * @param jsonActResult
     * @param jsonExpResult
     * @param resJsonData  什么？？？
     * @param dbJsonData   什么？？？
     */
    public static void verificationResponseResult(String filePath, String sheetName, int currenRow,
                                                  JSONObject jsonActResult,JSONObject jsonExpResult,
                                                  int row,String caseID,String caseName,JSONObject resJsonData,JSONObject dbJsonData){
        //实际与预期相同
        if(Assertion.verifyEquals(jsonActResult.toString(),jsonExpResult.toString())){
             //并且结果json串与db里面一致
            if(Assertion.verifyEquals(resJsonData.toString(),dbJsonData.toString())) {
                LoggerUtil.info("测试用例编号:" + caseID + ";用例名称：" + caseName + ";执行成功！！");
                ExcelUtil.setCellData(filePath, sheetName, currenRow, testResultNo, "Passed", true);
                ExcelUtil.setCellData(filePath, sheetName, currenRow, executeTimeNo, CommonUtil.getCurrentTime(), true);
                ExcelUtil.setCellData(filePath, sheetName, currenRow, actResultNo, jsonActResult.toString(), true);
                ExcelUtil.setCellData(filePath, sheetName, currenRow, commenNo, "用例执行通过", true);
            }else {
                LoggerUtil.info("测试用例编号:"+caseID+";用例名称："+caseName+";执行失败！！");
                ExcelUtil.setCellData(filePath,sheetName,currenRow,testResultNo,"Failed",false);
                ExcelUtil.setCellData(filePath,sheetName,currenRow,executeTimeNo,CommonUtil.getCurrentTime(),false);
                ExcelUtil.setCellData(filePath,sheetName,currenRow,actResultNo,resJsonData.toString(),false);
                ExcelUtil.setCellData(filePath,sheetName,currenRow,commenNo,"code一致，但data与预期不同",false);
            }
        }else {
            LoggerUtil.info("测试用例编号:"+caseID+";用例名称："+caseName+";执行失败！！");
            ExcelUtil.setCellData(filePath,sheetName,currenRow,testResultNo,"Failed",false);
            ExcelUtil.setCellData(filePath,sheetName,currenRow,executeTimeNo,CommonUtil.getCurrentTime(),false);
            ExcelUtil.setCellData(filePath,sheetName,currenRow,actResultNo,jsonActResult.toString(),false);
            ExcelUtil.setCellData(filePath,sheetName,currenRow,commenNo,"code与预期不同",false);
        }
        row++;
    }
}
