package Exo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnecton {
   private String url = "jdbc:postgresql://localhost:5432/product_management_db";
   private String user = "product_manager_user";
   private String pwd = "123456";

   public Connection getDBConnection () {
       try {
           return DriverManager.getConnection(url, user, pwd);
       }catch (SQLException e) {
           e.printStackTrace();
           return null;
       }
    }
}