package com.sxy.testcase;

import com.alibaba.fastjson.JSONObject;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.Map;

public class My {

    @Test
    public void testJsonSort(){
//        String s = "{\"total\":\"2\",\"rows\":[{\"noticeContent\":\"通知放假时间为1-3号\",\"createBy\":\"admin\",\"createTime\":\"2022-05-07 17:06:21\",\"updateBy\":\"\",\"noticeType\":\"1\",\"params\":{},\"noticeId\":12,\"noticeTitle\":\"公告20220507170623\",\"status\":\"0\"},{\"noticeContent\":\"通知放假时间为1-3号\",\"createBy\":\"admin\",\"createTime\":\"2022-05-07 17:29:18\",\"updateBy\":\"\",\"noticeType\":\"1\",\"params\":{},\"noticeId\":11,\"noticeTitle\":\"公告20220507172920\",\"status\":\"0\"}]}";
//        JSONObject json = JSONObject.parseObject(s);

//        JSONObject jsonsort = SortUtil.srotJsonObjByKey(json,"rows","noticeId");
//        System.out.println(jsonsort.toJSONString());

        Map<String,Object> map = new LinkedHashMap<String,Object>();
        map.put("id",1);
        map.put("name","张三");
        JSONObject obj = new JSONObject(map);
        System.out.println(obj.toJSONString());
        System.out.println(obj.toString());
    }
}
