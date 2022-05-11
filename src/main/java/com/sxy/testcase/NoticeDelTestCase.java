package com.sxy.testcase;

import com.google.gson.JsonObject;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonParser;
import com.sxy.utils.CommonUtil;
import com.sxy.utils.JDBCUtils;
import com.sxy.utils.LoggerUtil;
import org.springframework.jdbc.core.JdbcTemplate;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

/**
 * 比较两个txt文件中的json内容
 */
public class NoticeDelTestCase {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Test(description = "删除操作")
    public void tearDown() {
        String sql = "DELETE FROM sys_notice where notice_id != 1";
        int i = template.update(sql);
        LoggerUtil.info("影响的条数：" + i);
    }

    @Test
    public void test(){
        String pathA = this.getClass().getResource("/jsontest/jsonA.txt").getPath();
        String pathB = this.getClass().getResource("/jsontest/jsonB.txt").getPath();
        String jonStr1 = CommonUtil.txt2String(new File(pathA));
        String jonStr2 = CommonUtil.txt2String(new File(pathB));
        System.out.println(jonStr1);

        JSONObject obj1 =JSONObject.parseObject(jonStr1);
        JSONObject obj2 =JSONObject.parseObject(jonStr2);

        Assert.assertEquals(obj1,obj2);
    }
}
