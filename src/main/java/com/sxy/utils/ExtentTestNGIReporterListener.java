package com.sxy.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.model.TestAttribute;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;
import org.testng.*;
import org.testng.xml.XmlSuite;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.*;

public class ExtentTestNGIReporterListener implements IReporter {
    static Date date = new Date();
    static SimpleDateFormat format = new SimpleDateFormat("yyyyMMddhhmmss");
    static String time = format.format(date);
    //生成的路径以及文件名
    private static final String OUTPUT_FOLDER = "test-output/";
    private static final String FILE_NAME = "indexsxy"+time+".html";

    private ExtentReports extent;

     @Override
    public void generateReport(List<XmlSuite> xmlSuites, List<ISuite> iSuites, String outputDirectory) {
         //调用方法进行初始化
         init();
         boolean createSuiteNode = false;
         if(iSuites.size()>1){
             createSuiteNode = true;
         }
         for (ISuite suite : iSuites) {
             Map<String, ISuiteResult> result = suite.getResults();
             //如果suite里面没有任何用例，直接跳过，不在报告里生成
             if (result.size() == 0) {
                 continue;
             }
             //统计suite下的成功、失败、跳过的总用例数
             int suiteFailSize = 0;
             int suitePassSize = 0;
             int suiteSkipSize = 0;
             ExtentTest suiteTest = null;
             //存在多个suite的情况下，在报告中将同一个suite的测试结果归为一类，创建一级节点。
             if (createSuiteNode) {
                 suiteTest = extent.createTest(suite.getName()).assignCategory(suite.getName());
             }
             boolean createSuiteResultNode = false;
             if (result.size() > 1) {
                 createSuiteResultNode = true;
             }
             for (ISuiteResult r:result.values()) {
                 ExtentTest resultNode;
                 ITestContext context = r.getTestContext();
                 if (createSuiteResultNode) {
                     //没有创建suite的情况下，将在SuiteResult的创建为一级节点，否则创建为suite的一个子节点。
                     if (null == suiteTest) {
                         resultNode = extent.createTest(r.getTestContext().getName());
                     } else {
                         resultNode = suiteTest.createNode(r.getTestContext().getName());
                     }
                 }else {
                     resultNode = suiteTest;
                 }
                 if (resultNode != null) {
                     resultNode.getModel().setName(suite.getName() + " : " + r.getTestContext().getName());
                     if (resultNode.getModel().hasCategory()) {
                         resultNode.assignCategory(r.getTestContext().getName());
                     } else {
                         resultNode.assignCategory(suite.getName(), r.getTestContext().getName());
                     }
                     resultNode.getModel().setStartTime(r.getTestContext().getStartDate());
                     resultNode.getModel().setEndTime(r.getTestContext().getEndDate());
                     //统计SuiteResult下的数据
                     int passSize = r.getTestContext().getPassedTests().size();
                     int failSize = r.getTestContext().getFailedTests().size();
                     int skipSize = r.getTestContext().getSkippedTests().size();
                     suitePassSize += passSize;
                     suiteFailSize += failSize;
                     suiteSkipSize += skipSize;
                     if (failSize > 0) {
                         resultNode.getModel().setStatus(Status.FAIL);
                     }
                     resultNode.getModel().setDescription(String.format("Pass: %s ; Fail: %s ; Skip: %s ;", passSize, failSize, skipSize));
                 }
                 buildTestNodes(resultNode, context.getFailedTests(), Status.FAIL);
                 buildTestNodes(resultNode, context.getSkippedTests(), Status.SKIP);
                 buildTestNodes(resultNode, context.getPassedTests(), Status.PASS);
             }
             if (suiteTest != null) {
                 suiteTest.getModel().setDescription(String.format("Pass: %s ; Fail: %s ; Skip: %s ;", suitePassSize, suiteFailSize, suiteSkipSize));
                 if (suiteFailSize > 0) {
                     suiteTest.getModel().setStatus(Status.FAIL);
                 }
             }
         }
             extent.flush();
    }

    private void buildTestNodes(ExtentTest extenttest, IResultMap tests, Status status) {
        //存在父节点时，获取父节点的标签
         String[] categories = new String[0];
         if(extenttest != null){
             List<TestAttribute> categoryList = extenttest.getModel().getCategoryContext().getAll();
             categories = new String[categoryList.size()];//父节点数组
             //遍历，将list节点依次存入数组中
             for (int index = 0; index < categoryList.size(); index++) {
                 categories[index] = categoryList.get(index).getName();
             }
         }
         ExtentTest test;
         if(tests.size()>0){//用例集合长度>0也就是不为空
             //调整用例排序，按时间排序
             Set<ITestResult> treeSet = new TreeSet<>(new Comparator<ITestResult>() {
                 @Override
                 public int compare(ITestResult o1, ITestResult o2) {
                     return o1.getStartMillis() < o2.getStartMillis() ? -1 : 1;
                 }
             });
             treeSet.addAll(tests.getAllResults());
             //遍历用例
             for (ITestResult result: treeSet) {
                 String name ="";
                 //如果有参数，则使用参数的toString组合代替报告中的name
                /*Object[] parameters = result.getParameters();
                for(Object param:parameters){
                    name+=param.toString();
                }*/
                 if(name.length()>0){
                     if(name.length()>50){
                         name = name.substring(0,49)+"...";//大于50就取前49个....形式
                     }
                 }else {//正常取方法名---方法描述
                     name = result.getMethod().getMethodName()+"---"+result.getMethod().getDescription();
                 }
                 if(extenttest == null){
                     test = extent.createTest(name);//重新用例
                 }else {
                     //作为子节点进行创建时，设置同父节点的标签一致，便于报告检索。
                     test = extenttest.createNode(name).assignCategory(categories);
                 }
                 //test.getModel().setDescription(description.toString());
                 //test = extent.createTest(result.getMethod().getMethodName());
                 //遍历测试方法的组，将组分类
                 for (String group:result.getMethod().getGroups()) {
                     test.assignCategory(group);

                     List<String> outputList = Reporter.getOutput(result);
                     for (String output:outputList) {
                         //将用例的log输出报告中
                         test.debug(output);
                     }
                     if(result.getThrowable()!=null){
                         test.log(status,result.getThrowable());//日志输出异常
                     }else {
                         test.log(status,"Test"+status.toString().toLowerCase()+"ed");
                     }
                     test.getModel().setStartTime(getTime(result.getStartMillis()));
                     test.getModel().setEndTime(getTime(result.getEndMillis()));
                 }
             }
         }
    }

    private Date getTime(long millis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(millis);
        return calendar.getTime();
    }

    private void init() {
         //文件夹不存在时，创建文件夹
         File reportDir = new File(OUTPUT_FOLDER);
         if(!reportDir.exists()&&!reportDir.isDirectory()){
             reportDir.mkdir();
         }
         ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(OUTPUT_FOLDER+FILE_NAME);
        // 设置静态文件的DNS
        //怎么样解决cdn.rawgit.com访问不了的情况
        htmlReporter.config().setResourceCDN(ResourceCDN.EXTENTREPORTS);
        
        htmlReporter.config().setDocumentTitle("Wconcept-Api自动化测试报告");
        htmlReporter.config().setReportName("Wconcept-Api自动化测试报告");
        htmlReporter.config().setChartVisibilityOnOpen(true);
        htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
        htmlReporter.config().setTheme(Theme.STANDARD);
        htmlReporter.config().setCSS(".node.level-1  ul{ display:none;} .node.level-1.active ul{display:block;}");
        //解决报告中中文乱码
//        htmlReporter.config().setEncoding("gbk");
        extent = new ExtentReports();
        extent.attachReporter(htmlReporter);
        extent.setReportUsesManualConfiguration(true);
    }
}
