package lk.ijse.util;

import lk.ijse.listener.Listener;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.servlet.ServletContext;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CrudUtil {
    static ServletContext servletContext = Listener.getServletContext();
    static BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");

    public static <T> T setQuery(String query, Object... args) throws SQLException {
        try(Connection connection = dbcp.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            for (int i = 0; i < args.length; i++) {
                preparedStatement.setObject(i + 1, args[i]);
            }

            if (query.startsWith("SELECT") || query.startsWith("select")) {
                return (T) preparedStatement.executeQuery();
            } else {
                return (T) (Boolean) (preparedStatement.executeUpdate() > 0);
            }
        }
    }
}
