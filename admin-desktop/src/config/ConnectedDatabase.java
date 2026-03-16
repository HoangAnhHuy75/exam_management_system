package config;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectedDatabase {

    public static Connection getConnectedDB() {
        Connection c = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/exam_management_system?useSSL=false&serverTimezone=UTC&characterEncoding=UTF-8";
            String username = "root";
            String password = "root";
            c = DriverManager.getConnection(url, username, password);
            System.out.println("Ket noi MySQL thanh cong!");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ket noi co so du lieu that bai");
        }
        return c;
    }

    public static void closeConnectedDB(Connection c) {
        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}