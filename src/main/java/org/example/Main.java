package org.example;
import io.github.cdimascio.dotenv.Dotenv;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("URL");
        String user = dotenv.get("USER");
        String pwd = dotenv.get("PASSWORD");

        try (Connection conn = DriverManager.getConnection(url, user, pwd)) {
            System.out.println("Connected to the database");
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

}