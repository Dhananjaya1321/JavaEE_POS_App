package lk.ijse.db;

import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    /*private static DBConnection dbConnection;
    private Connection connection;

    private DBConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost/spa", "root", "1234");

        } catch (ClassNotFoundException | SQLException exception) {
            exception.printStackTrace();
        }
    }

    public static DBConnection getDbConnection(){
        return dbConnection==null ? dbConnection=new DBConnection() : dbConnection;
    }
    public Connection getConnection(){
        return connection;
    }*/
}
