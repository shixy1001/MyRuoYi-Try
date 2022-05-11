package com.sxy.utils;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class Assertion {
    public static boolean flag = true;
    public static List<Error> errors = new ArrayList<>();

    /**
     *
     * @param o
     * @return
     */
    public static boolean verifyTrue(boolean o){
        try {
            Assert.assertTrue(o);//运行成功与否
        }catch (Error e){
           errors.add(e);
           flag = false;
        }
        return flag;
    }

    /**
     *
     * @param o
     * @return
     */
    public static boolean verifyFalse(boolean o){
        try {
            Assert.assertFalse(o);//运行成功与否
        }catch (Error e){
            errors.add(e);
            flag = false;
        }
        return flag;
    }

    /**
     * 比较两个值是否相等
     * @param actual
     * @param expected
     * @return
     */
    public static boolean verifyEquals(Object actual,Object expected){
        try {
            Assert.assertEquals(actual, expected);//如果一致程序继续运行，如果不一致异常
        }catch (Error e){
            errors.add(e);
            flag=false;
        }
        return flag;
    }

    /**
     *
     * @param actual
     * @param expected
     * @param message
     */
    public static void verifyEquals(Object actual,Object expected,String message){
        try{
            Assert.assertEquals(actual,expected,message);
        }catch (Error e){
            errors.add(e);
            flag = false;
        }
    }

    /**
     *
     * @param actual
     * @param message
     */
    public static void verifyNulls(Object actual,String message){
        try{
            Assert.assertNull(actual,message);
        }catch (Error e){
            errors.add(e);
            flag = false;
        }//打印errors内容样式[java.lang.AssertionError: 测试行不行 expected [sone334] but found [sone333]]
    }

}
