package com.sxy.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.poi.util.StringUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortUtil {
    /**
     *https://www.yuque.com/asalan570/ggl4yu/ye6ti2
     * 倒序排序
     * @param list 需要排序的list
     * @param key 根据key来排序
     */
    public static void ListJSONObjectSortDESC(List list, final String key){
        Collections.sort(list, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject a,JSONObject b){
              //获取比较的第一个值和第二个值
              Float valA_1 = a.getFloat(key);
              Float valB_1 = b.getFloat(key);
              //调用compareTo 方法 比较  获取返回值
              //返回值大于0就是valA_1>valB_1,返回值=0就是一样大，返回值小于0就是valB_1大于valA_1
                final  int flag = valA_1.compareTo(valB_1);
                //判断返回值
                //正常情况下，返回1是后移，但是我们需要倒序排序，所以大于0和小于0的返回值我们相反
                if(flag>0){
                    return -1;
                }else if(flag == 0){
                    return 0;
                }else {
                    return 1;
                }
            }
        });
    }

    /**
     * 升序排序
     * @param list
     * @param key
     */
    public static void ListJSONObjectSortASC(List list, final String key){
        Collections.sort(list, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject a,JSONObject b){
                //获取比较的第一个值和第二个值
                Float valA_1 = a.getFloat(key);
                Float valB_1 = b.getFloat(key);
                //调用compareTo 方法 比较  获取返回值
                //返回值大于0就是valA_1>valB_1,返回值=0就是一样大，返回值小于0就是valB_1大于valA_1
                final  int flag = valA_1.compareTo(valB_1);
                //判断返回值
                //正常情况下，返回1是后移，但是我们需要倒序排序，所以大于0和小于0的返回值我们相反
                if(flag>0){
                    return 1;
                }else if(flag == 0){
                    return 0;
                }else {
                    return -1;
                }
            }
        });
    }

    /**
     * 对两组jsonarray进行对比，将json中的key提取集合，通过for循环依次比对每个key值
     * @param objA
     * @param objB
     * @return
     */
    public static boolean compareToAB(JSONArray objA,JSONArray objB){
        boolean b = true;
        StringBuffer sBuffer = new StringBuffer();
        int objALength = objA.size();
        int objBLength = objB.size();

        String successStr = "字段值相同:";
        String failStr = "字段值不同:";
        String propertyStr = "已扫描到的字段:";

        JSONObject objJsonA = null;
        JSONObject objJsonB = null;
        if(objALength == objBLength){
            for (int i = 0; i < objALength; i++) {
                objJsonA = objA.getJSONObject(i);
                objJsonB = objB.getJSONObject(i);
                for (String key:objJsonA.keySet()) {
                    //依次将key集合中的key去比对，B也有这个key，并且A\B key值相同，本key值匹配成功
                    if(objJsonB.containsKey(key)&& StringUtils.equals(objJsonA.getString(key),objJsonB.getString(key))){
                        System.out.println("successStr:"+key+","+successStr);
                    }else {
                        b = false;
                        System.out.println("failStr:"+key+","+failStr);
                    }
                    System.out.println("propertyStr:"+key+","+propertyStr);
                }
            }
        }else {
            b = false;
            sBuffer.append("两个对象长度不一致");
        }
        return b;
    }

    /**
     * 将json对象中的[]键提取为json数组，然后根据sortKey给数组中的对象进行排序，再将排序后的数组放回json对象返回
     * 实际就是将某个json对象中的数组内的对象根据sortKey进行排序并返回排序后的json对象
     * @param jsonRes
     * @param Key
     * @param sortKey
     */
    public static JSONObject srotJsonObjByKey(JSONObject jsonRes,String Key,String sortKey) {
           //将json对象中[]的键值提取为jsonArrayKey
            JSONArray jsonArrayKey =jsonRes.getJSONArray(Key);
          //转list
        List<JSONObject> list = JSONArray.parseArray(jsonArrayKey.toJSONString(), JSONObject.class);
        System.out.println("排序前："+jsonArrayKey);
        //根据sortKey将json对象进行排序
        Collections.sort(list, new Comparator<JSONObject>() {
            @Override
            public int compare(JSONObject o1, JSONObject o2) {
                int a = o1.getInteger(sortKey);//提取列表中每个对象的sortKey键值，通过此键进行排序
                int b = o2.getInteger(sortKey);
                if(a>b){
                    return 1;
                }else if(a<b){
                    return -1;
                }else {
                    return 0;
                }
            }
        });
        //排序后将列表再转回数组
        JSONArray jsonArray = JSONArray.parseArray(list.toString());
        System.out.println("排序后："+jsonArray);
        jsonRes.put(Key,jsonArray);
        return jsonRes;
    }
}
