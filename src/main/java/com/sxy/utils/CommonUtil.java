package com.sxy.utils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class CommonUtil {
    /**
     * 获取当前系统时间yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentTime(){
        Date currentTime = new Date();
        //日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //将当前时间格式化为字符串
        String dateStr = formatter.format(currentTime);
        return dateStr;
    }
    /**
     * 获取当前系统时间yyyyMMddHHmmss
     * @return
     */
    public static String getCurrentSysTime(){
        Date currentTime = new Date();
        //日期格式
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        //将当前时间格式化为字符串
        String dateStr = formatter.format(currentTime);
        return dateStr;
    }
    /**
     * 读取properties文件内容
     * @param propFile,此为文件绝对路径的字符串，如果相对路径找不到
     * D:\XXZL\AutoTest\MyRuoYi-Try\src\main\resources\log4j.properties
     * @return
     */
    public static Properties getPropertiesInstance(String propFile){
        Properties properties = new Properties();

        try {
            FileInputStream in = new FileInputStream(propFile);
            properties.load(in);
            in.close();
        }catch (IOException e) {
        e.printStackTrace();
        LoggerUtil.info("读取对象文件出错");
        }
        return properties;
    }
    /**
     * 使用ResourceBundle读取properties文件
     * https://www.cnblogs.com/sebastian-tyd/p/7895182.html
     * @param fileName,这里只写resources下的文件名就可以，不要后缀runsetting
     * @param key
     * @return
     */
    public static String getPropertiesInfo(String fileName,String key){
        ResourceBundle resource = ResourceBundle.getBundle(fileName);
        String valueInfo = resource.getString(key);
        return valueInfo;
    }

    /**
     * 方法一：比较两个list
     * @param list1
     * @param list2
     * @param <T>
     * @return
     */
    public static <T extends  Comparable<T>> boolean isEquals(List<T> list1, List<T> list2){
        if(list1 == null && list2 == null){
            return true;
        }
        //Only one of them is null
        else if(list1 == null || list2 == null){
            return false;
        }
        else if(list1.size() != list2.size()){
            return false;
        }
        //当都不为空并且长度相同的时候，开始比较内容
        // copying to avoid rearranging original lists
       list1 = new ArrayList<T>(list1);//构造一个包含指定collection的元素的列表
        list2 = new ArrayList<T>(list2);//按照collection的迭代器返回他们的顺序排列

        Collections.sort(list1);
        Collections.sort(list2);

        return list1.equals(list2);
    }

    /**
     * 方法二：使用这个方法比对，也可以使用testng自带的断言方式。
     * @param actList
     * @param dbList
     * List=null是指没有给它分配空间，这个东西就不存在，只有一个定义。
     * 而new了之后东西已经存在，只是么有内容，size=0或者isEmpty来判断是否有内容。
     * @return
     */
    public static boolean isEqualList(List<String> actList, List<String> dbList){
         if(null != actList && null != dbList){
             Collections.sort(actList);
             Collections.sort(dbList);
             //containsAll() list中是否包含另一个集合的全部元素，如果两个互相都包含，那就是相等。
             if(actList.containsAll(dbList) && dbList.containsAll(actList)){
                 return true;
             }
         }
         return false;
    }

    public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        try {
            //构造一个BufferedReader类来读取文件，此适用于没有中文汉字或者编码都一致的情况
            //  BufferedReader br = new BufferedReader(new FileReader(file));

            //中文乱码，使用InputStreamReader代替，进行指定编码设置
            InputStreamReader isr = new InputStreamReader(new FileInputStream(file),"UTF-8");
            BufferedReader br = new BufferedReader(isr);//通过输入流设置编码
            String s=null;
            while((s=br.readLine())!= null){//使用readLine方法，一次读一行
              //  s =br.readLine();//while()已经度过一行了，如果再要此句，每隔一行读取一句，内容错误。
                //  line.separator行分隔符，不管wind还是linux都适用。 如果/n只能linux,/r/n只能wind，
                result.append(System.lineSeparator()+s);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.toString();
    }
}
