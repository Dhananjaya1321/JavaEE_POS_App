package lk.ijse.servlet;

import lk.ijse.db.DBConnection;

import javax.json.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
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
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Content-Type", "application/json");

        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        String orderId = jsonObject.getString("orderId");
        String date = jsonObject.getString("date");
        String nic = jsonObject.getString("nic");
        double total = Double.parseDouble(jsonObject.getString("total"));
        double subTotal = Double.parseDouble(jsonObject.getString("subTotal"));
        double cash = Double.parseDouble(jsonObject.getString("cash"));
        int discount = Integer.parseInt(jsonObject.getString("discount"));
        double balance = Double.parseDouble(jsonObject.getString("balance"));

        try {
            try {
                connection.setAutoCommit(false);

                PreparedStatement orderStatement = connection.prepareStatement("INSERT INTO orders VALUES (?,?,?,?,?,?,?,?)");
                orderStatement.setObject(1, orderId);
                orderStatement.setObject(2, date);
                orderStatement.setObject(3, nic);
                orderStatement.setObject(4, total);
                orderStatement.setObject(5, subTotal);
                orderStatement.setObject(6, cash);
                orderStatement.setObject(7, balance);
                orderStatement.setObject(8, discount);

                if (orderStatement.executeUpdate() > 0) {
                    int count = 0;
                    JsonArray cartItems = jsonObject.getJsonArray("cartItems");
                    for (int i = 0; i < cartItems.size(); i++) {
                        PreparedStatement orderDetailsStatement = connection.prepareStatement("INSERT INTO orderdetails VALUES (?,?,?,?)");
                        JsonObject cartItem = cartItems.getJsonObject(i);

                        String itemCode = cartItem.getString("itemCode");
                        double itemPrice = Double.parseDouble(cartItem.getString("itemPrice"));
                        int itemQty = Integer.parseInt(cartItem.getString("itemQty"));

                        orderDetailsStatement.setObject(1, orderId);
                        orderDetailsStatement.setObject(2, itemCode);
                        orderDetailsStatement.setObject(3, itemQty);
                        orderDetailsStatement.setObject(4, itemPrice);

                        if (orderDetailsStatement.executeUpdate() > 0) {
                            count++;
                        }
                    }
                    if (count == cartItems.size()) {
                        count = 0;
                        for (int i = 0; i < cartItems.size(); i++) {
                            JsonObject cartItem = cartItems.getJsonObject(i);

                            String itemCode = cartItem.getString("itemCode");
                            int itemQty = Integer.parseInt(cartItem.getString("itemQty"));

                            PreparedStatement preparedStatement = connection.prepareStatement("SELECT qty FROM item WHERE code=?");
                            preparedStatement.setObject(1, itemCode);

                            ResultSet resultSet = preparedStatement.executeQuery();
                            System.out.println(resultSet.next());/////////
                            int oldQTY= Integer.parseInt(resultSet.getString(1));

                            PreparedStatement updateItemsStatement = connection.prepareStatement("UPDATE item SET qty=? WHERE code=?");
                            updateItemsStatement.setObject(1, oldQTY-itemQty);
                            updateItemsStatement.setObject(2, itemCode);

                            if (updateItemsStatement.executeUpdate() > 0) {
                                count++;
                            }
                        }

                        if (count == cartItems.size()) {
                            connection.commit();
                            resp.getWriter().print(
                                    Json.createObjectBuilder()
                                            .add("state", "Ok")
                                            .add("message", "Successfully Added...!")
                                            .add("data", "[]")
                                            .build()
                            );
                        }
                    }
                }
            } catch (Exception e) {
                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
                connection.close();
            }
        } catch (SQLException e) {

        } finally {

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
        resp.addHeader("Access-Control-Allow-Origin", "*");
        resp.addHeader("Access-Control-Allow-Methods", "PUT,DELETE");
        resp.addHeader("Access-Control-Allow-Headers", "Content-Type");
    }
}
