package lk.ijse.servlet;

import lk.ijse.db.DBConnection;

import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObjectBuilder;
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
    Connection connection = DBConnection.getDbConnection().getConnection();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM item");
            ResultSet resultSet = preparedStatement.executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while (resultSet.next()) {
                JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                objectBuilder.add("code", resultSet.getString(1));
                objectBuilder.add("name", resultSet.getString(2));
                objectBuilder.add("price", resultSet.getString(3));
                objectBuilder.add("qty", resultSet.getString(4));
                arrayBuilder.add(objectBuilder.build());
            }
            resp.getWriter().print(arrayBuilder.build());
        } catch (SQLException e) {

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");
        String code=req.getParameter("code");
        String name=req.getParameter("name");
        double price= Double.parseDouble(req.getParameter("price"));
        int qty= Integer.parseInt(req.getParameter("qty"));
        System.out.println(code+" "+name+" "+price+" "+qty);
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO item VALUES (?,?,?,?)");
            preparedStatement.setObject(1, code);
            preparedStatement.setObject(2, name);
            preparedStatement.setObject(3, price);
            preparedStatement.setObject(4, qty);
            if (preparedStatement.executeUpdate() > 0) {
                resp.getWriter().print(
                        Json.createObjectBuilder()
                                .add("state", "Ok")
                                .add("message", "Successfully Added...!")
                                .add("data", "[]")
                                .build()
                );
            }
        } catch (SQLException e) {

        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
