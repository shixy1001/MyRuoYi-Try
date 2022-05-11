package com.sxy.utils;
import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * druid连接池工具
 */
public class JDBCUtils {
    private static DataSource ds;//定义成员变量

    static {
        try {
        Properties pro = new Properties();//加载配置文件
        InputStream fin = JDBCUtils.class.getClassLoader().getResourceAsStream("druid.properties");
        pro.load(fin);
        ds = DruidDataSourceFactory.createDataSource(pro);//获取DataSource
        }  catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取链接的方法
     * @return
     * @throws SQLException
     */
    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    /**
     * 释放资源,执行DML需要Statement和Connection
     * @param temt
     * @param conn
     */
    public static void close(Statement temt, Connection conn){
        if(temt !=null){
            try {
                temt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    /**
     * 释放归还连接，重载
     * 执行DQL需要ResultSet,Statement,Connection
     * @param rs
     * @param temt
     * @param conn
     */
    public static void close(ResultSet rs, Statement temt, Connection conn){
        if(rs != null){
            try {
                rs.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(temt !=null){
            try {
                temt.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }

    public static DataSource getDataSource(){
        return ds;
    }
}
