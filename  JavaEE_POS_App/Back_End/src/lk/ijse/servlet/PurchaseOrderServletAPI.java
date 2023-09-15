package lk.ijse.servlet;

import lk.ijse.bo.BOTypes;
import lk.ijse.bo.FactoryBO;
import lk.ijse.bo.SuperBO;
import lk.ijse.bo.castom.impl.OrderBOImpl;
import lk.ijse.dto.CustomDTO;
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
import java.util.ArrayList;

@WebServlet(urlPatterns = "/pages/order")
public class PurchaseOrderServletAPI extends HttpServlet {
    private final OrderBOImpl orderBO = (OrderBOImpl) FactoryBO.getFactoryBO().getInstance(BOTypes.ORDER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String option = req.getParameter("option");
        ServletContext servletContext = getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection = dbcp.getConnection()) {
            switch (option) {
                case "orders":
                    PreparedStatement preparedStatement1 = connection.prepareStatement("SELECT orderID FROM orders");
                    ResultSet resultSet1 = preparedStatement1.executeQuery();
                    JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

                    System.out.println("orders ok");

                    while (resultSet1.next()) {
                        arrayBuilder.add(
                                Json.createObjectBuilder()
                                        .add("orderID", resultSet1.getString(1))
                        );
                    }
                    resp.getWriter().print(
                            Json.createObjectBuilder()
                                    .add("state", "Ok")
                                    .add("message", "Successfully loaded...!")
                                    .add("data", "[" + arrayBuilder.build() + "]")
                                    .build()
                    );

                    break;
                case "orderCount":
                    PreparedStatement preparedStatement2 = connection.prepareStatement("SELECT COUNT(orderID) as orderCount FROM orders");
                    ResultSet resultSet = preparedStatement2.executeQuery();
                    JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

                    System.out.println("orderCount ok");

                    if (resultSet.next()) {
                        objectBuilder.add("ordersCount", resultSet.getString(1));
                    }
                    resp.getWriter().print(
                            Json.createObjectBuilder()
                                    .add("state", "Ok")
                                    .add("message", "Successfully loaded...!")
                                    .add("data", "[" + objectBuilder.build() + "]")
                                    .build()
                    );
                    break;
                case "orderDetails":
                    String orderID = req.getParameter("orderID");
                    PreparedStatement getOrderStatement = connection.prepareStatement("SELECT * FROM orders WHERE orderID=?");
                    getOrderStatement.setObject(1, orderID);
                    ResultSet order = getOrderStatement.executeQuery();

                    JsonArrayBuilder array = Json.createArrayBuilder();

                    System.out.println("orderDetails ok");

                    if (order.next()) {
                        String date = order.getString(2);
                        String nic = order.getString(3);
                        String total = order.getString(4);
                        String subTotal = order.getString(5);
                        String cash = order.getString(6);
                        String balance = order.getString(7);
                        String discount = order.getString(8);

                        array.add(Json.createObjectBuilder()
                                .add("date", date)
                                .add("nic", nic)
                                .add("total", total)
                                .add("subTotal", subTotal)
                                .add("cash", cash)
                                .add("balance", balance)
                                .add("discount", discount)
                                .build()
                        );

                        System.out.println(1);

                        PreparedStatement getOrderDetailsStatement = connection.prepareStatement("SELECT * FROM orderDetails WHERE orderID=?");
                        getOrderDetailsStatement.setObject(1, orderID);
                        ResultSet orderDetails = getOrderDetailsStatement.executeQuery();

                        System.out.println(2);

                        JsonArrayBuilder orderDetailsArray = Json.createArrayBuilder();
                        while (orderDetails.next()) {
                            String code = orderDetails.getString(2);
                            String price = orderDetails.getString(3);
                            String qty = orderDetails.getString(4);

                            PreparedStatement getItemStatement = connection.prepareStatement("SELECT name FROM item WHERE code=?");
                            getItemStatement.setObject(1, code);

                            ResultSet itemDetails = getItemStatement.executeQuery();

                            System.out.println(3);

                            String itemName = null;
                            if (itemDetails.next()) {
                                itemName = itemDetails.getString(1);
                            }

                            orderDetailsArray.add(
                                    Json.createObjectBuilder()
                                            .add("itemCode", code)
                                            .add("itemName", itemName)
                                            .add("itemPrice", price)
                                            .add("itemQty", qty)
                                            .build()
                            );
                        }
                        array.add(orderDetailsArray.build());

                        System.out.println(4);

                        PreparedStatement getCustomerDetailsStatement = connection.prepareStatement("SELECT * FROM customer WHERE nic=?");
                        getCustomerDetailsStatement.setObject(1, nic);
                        ResultSet customerDetails = getCustomerDetailsStatement.executeQuery();
                        if (customerDetails.next()) {
                            String cusName = customerDetails.getString(2);
                            String tel = customerDetails.getString(3);
                            String address = customerDetails.getString(4);

                            System.out.println(5);

                            array.add(
                                    Json.createObjectBuilder()
                                            .add("nic", nic)
                                            .add("name", cusName)
                                            .add("tel", tel)
                                            .add("address", address)
                                            .build()
                            );
                        }
                    }

                    resp.getWriter().print(
                            Json.createObjectBuilder()
                                    .add("state", "Ok")
                                    .add("message", "Successfully loaded...!")
                                    .add("data", "[" + array.build() + "]")
                                    .build()
                    );
                    break;
            }
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(
                    Json.createObjectBuilder()
                            .add("state", "Error")
                            .add("message", e.getMessage())
                            .add("data", "[]")
                            .build()
            );
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonReader reader = Json.createReader(req.getReader());
        JsonObject jsonObject = reader.readObject();

        ArrayList<CustomDTO> customDTOS = new ArrayList<>();

        String orderId = jsonObject.getString("orderId");
        CustomDTO dto = new CustomDTO(
                orderId,
                jsonObject.getString("date"),
                jsonObject.getString("nic"),
                Double.parseDouble(jsonObject.getString("total")),
                Double.parseDouble(jsonObject.getString("subTotal")),
                Double.parseDouble(jsonObject.getString("cash")),
                Integer.parseInt(jsonObject.getString("discount")),
                Double.parseDouble(jsonObject.getString("balance"))
        );

        ServletContext servletContext = getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = dbcp.getConnection()) {
            try {

                if (orderStatement.executeUpdate() > 0) {
                    JsonArray cartItems = jsonObject.getJsonArray("cartItems");
                    for (int i = 0; i < cartItems.size(); i++) {
                        JsonObject cartItem = cartItems.getJsonObject(i);
                        customDTOS.add(
                                new CustomDTO(
                                        orderId,
                                        cartItem.getString("itemCode"),
                                        Double.parseDouble(cartItem.getString("itemPrice")),
                                        Integer.parseInt(cartItem.getString("itemQty"))
                                )
                        );
                    }
                    if (count == cartItems.size()) {
                        for (int i = 0; i < cartItems.size(); i++) {
                            JsonObject cartItem = cartItems.getJsonObject(i);

                            String itemCode = cartItem.getString("itemCode");
                            int itemQty = Integer.parseInt(cartItem.getString("itemQty"));

                            PreparedStatement preparedStatement = connection.prepareStatement("SELECT qty FROM item WHERE code=?");
                            preparedStatement.setObject(1, itemCode);

                            ResultSet resultSet = preparedStatement.executeQuery();
                            System.out.println(resultSet.next());/////////
                            int oldQTY = Integer.parseInt(resultSet.getString(1));

                            PreparedStatement updateItemsStatement = connection.prepareStatement("UPDATE item SET qty=? WHERE code=?");
                            updateItemsStatement.setObject(1, oldQTY - itemQty);
                            updateItemsStatement.setObject(2, itemCode);

                            if (updateItemsStatement.executeUpdate() > 0) {
                                count++;
                            }
                        }
                        dto.setCustomDTOS(customDTOS);


                        orderBO.saveOrder(dto);
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
            resp.setStatus(400);
            resp.getWriter().print(
                    Json.createObjectBuilder()
                            .add("state", "Error")
                            .add("message", e.getMessage())
                            .add("data", "[]")
                            .build()
            );
        }
    }
}
