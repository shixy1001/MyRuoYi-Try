package com.sxy.testcase;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxy.utils.ExtentTestNGIReporterListener;
import com.sxy.utils.JDBCUtils;
import com.sxy.utils.SortUtil;
import netscape.javascript.JSObject;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MyTest {
    public static void main(String[] args) throws IOException {
        String  filePath = MyTest.class.getResource("/JsonText.txt").getPath();
        File file = new File(filePath);
        BufferedReader fs = new BufferedReader(new FileReader(file));

        String text="";
        String line="";
        while((line =fs.readLine())!= null){
            text = text+line;
        }

        System.out.println(text);

        String key = "number";
        List<JSONObject> list = JSON.parseArray(text,JSONObject.class);
        System.out.println("list="+list);

        SortUtil.ListJSONObjectSortASC(list,key);
        System.out.println(list);
    }

    @Test
    public void JsonTest() throws SQLException {
        JDBCUtils.getDataSource();
        JDBCUtils.getConnection();
    }
    @Test
    public void testtofail(){
        System.out.println("fillll");
        Assert.assertTrue(false);
    }

}
