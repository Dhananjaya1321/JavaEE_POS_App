package lk.ijse.servlet;


import org.apache.commons.dbcp2.BasicDataSource;

import javax.json.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@WebServlet(urlPatterns = "/pages/item")
public class ItemServletAPI extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        ServletContext servletContext = getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");


        try (Connection connection=dbcp.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while (resultSet.next()) {
                arrayBuilder.add(Json.createObjectBuilder()
                        .add("code", resultSet.getString(1))
                        .add("name", resultSet.getString(2))
                        .add("price", resultSet.getString(3))
                        .add("qty", resultSet.getString(4))
                        .build()
                );
            }
            resp.getWriter().print(
                    objectBuilder
                            .add("state", "Ok")
                            .add("message", "Successfully loaded...!")
                            .add("data", "[" + arrayBuilder.build() + "]")
                            .build()
            );
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(
                    objectBuilder
                            .add("state", "Error")
                            .add("message", e.getMessage())
                            .add("data", "[]")
                            .build()
            );
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        String code = req.getParameter("code");
        String name = req.getParameter("name");
        double price = Double.parseDouble(req.getParameter("price"));
        int qty = Integer.parseInt(req.getParameter("qty"));
        ServletContext servletContext = getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection=dbcp.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            preparedStatement.setObject(1, code);
            preparedStatement.setObject(2, name);
            preparedStatement.setObject(3, price);
            preparedStatement.setObject(4, qty);
            if (preparedStatement.executeUpdate() > 0) {
                resp.getWriter().print(
                        objectBuilder
                                .add("state", "Ok")
                                .add("message", "Successfully Added...!")
                                .add("data", "[]")
                                .build()
                );
            }
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(
                    objectBuilder
                            .add("state", "Error")
                            .add("message", e.getMessage())
                            .add("data", "[]")
                            .build()
            );
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();
        String code = jsonObject.getString("code");
        String name = jsonObject.getString("name");
        double price = Double.parseDouble(jsonObject.getString("price"));
        int qty = Integer.parseInt(jsonObject.getString("qty"));

        ServletContext servletContext = getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection=dbcp.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE item SET name=?,price=?,qty=? WHERE code=?");
            preparedStatement.setObject(1, name);
            preparedStatement.setObject(2, price);
            preparedStatement.setObject(3, qty);
            preparedStatement.setObject(4, code);
            if (preparedStatement.executeUpdate() > 0) {
                resp.getWriter().print(
                        objectBuilder
                                .add("state", "Ok")
                                .add("message", "Successfully Updated...!")
                                .add("data", "[]")
                                .build()
                );
            }
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(
                    objectBuilder
                            .add("state", "Error")
                            .add("message", e.getMessage())
                            .add("data", "[]")
                            .build()
            );
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
        String code = req.getParameter("code");
        ServletContext servletContext = getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection=dbcp.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM item WHERE code=? ");
            preparedStatement.setObject(1, code);
            if (preparedStatement.executeUpdate() > 0) {
                resp.getWriter().print(
                        objectBuilder
                                .add("state", "Ok")
                                .add("message", "Successfully Deleted...!")
                                .add("data", "[]")
                                .build()
                );
            }
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(
                    objectBuilder
                            .add("state", "Error")
                            .add("message", e.getMessage())
                            .add("data", "[]")
                            .build()
            );
        }
    }
}
