package lk.ijse.servlet;

import lk.ijse.db.DBConnection;

import javax.json.*;
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

@WebServlet(urlPatterns = "/pages/order")
public class PurchaseOrderServletAPI extends HttpServlet {
    Connection connection = DBConnection.getDbConnection().getConnection();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");
        String option = req.getParameter("option");
        try {
            switch (option) {
                case "orders":
                    PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT orderID FROM orders");
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
                    while (resultSet1.next()) {
                        arrayBuilder.add(
                                Json.createObjectBuilder()
                                        .add("orderID", resultSet1.getString(1))
                        );
                    }
                    resp.getWriter().print(arrayBuilder.build());
                    break;
                case "orderCount":
                    PreparedStatement preparedStatement = connection.prepareStatement("SELECT COUNT(orderID) as orderCount FROM orders");
                    ResultSet resultSet = preparedStatement.executeQuery();
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();
                    if (resultSet.next()) {
                        objectBuilder.add("ordersCount", resultSet.getString(1));
                    }
                    resp.getWriter().print(objectBuilder.build());
                    break;
            }


        } catch (SQLException e) {

        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }

    @Override
    protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT,DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
