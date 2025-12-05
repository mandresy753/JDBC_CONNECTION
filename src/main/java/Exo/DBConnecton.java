package Exo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnecton {
    String url = "jdbc:postgresql://localhost:5432/product_management_db";
    String user = "product_manager_user";
    String pwd = "123456";

   public Connection getDBConnection () {
       try {
           return DriverManager.getConnection(url, user, pwd);
       }catch (SQLException e) {
           e.printStackTrace();
           return null;
       }
    }
}