package main;

import java.sql.*;

public class readInfoTest {
    public static String url = "jdbc:mysql://127.0.0.1:3306/planewar?" +
            "useUnicode=true&characterEncoding=UTF-8&userSSL=false&serverTimezone=GMT%2B8";
    public static String user = "root";
    public static String password = "zyh20000205";
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Statement stat = null;
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con = DriverManager.getConnection(url, user, password);
        stat = con.createStatement();//�ҵ����
        String sql = "select max(score) score from score;";//��ѯ���
        ResultSet rs=stat.executeQuery(sql);//��ѯ
//����ѯ���Ľ�����
        while (rs.next()) {
            String score=rs.getString("score");

            System.out.print(score);
        }
        rs.close();
        stat.close();
        con.close();
    }
}
