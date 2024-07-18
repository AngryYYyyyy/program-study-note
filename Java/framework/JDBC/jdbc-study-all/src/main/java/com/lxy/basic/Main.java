package com.lxy.basic;

import java.sql.*;

/**
 * @Author ：AngryYYYYYY
 * @Date ：Created in 2024/7/18 9:37
 * @Description：
 */
public class Main {
    public static void main(String[] args) throws SQLException {
        Driver driver = new com.mysql.cj.jdbc.Driver();
        DriverManager.registerDriver(driver);
        String url = "jdbc:mysql://127.0.0.1:3306/mybatisdb?useSSL=false&useUnicode=true&characterEncoding=UTF-8&serverTimezone=Asia/Shanghai&allowPublicKeyRetrieval=true";
        String username = "root";
        String password = "root";
        Connection conn = DriverManager.getConnection(url, username, password);
        Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery("select * from user");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            System.out.println(name);
        }
    }
}
