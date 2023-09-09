package lk.ijse.servlet;

import lk.ijse.bo.BOTypes;
import lk.ijse.bo.FactoryBO;
import lk.ijse.bo.SuperBO;
import lk.ijse.bo.castom.impl.CustomerBOImpl;
import lk.ijse.dto.CustomerDTO;
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

@WebServlet(urlPatterns = "/pages/customer")
public class CustomerServletAPI extends HttpServlet {
    CustomerBOImpl customerBO = (CustomerBOImpl) FactoryBO.getFactoryBO().getInstance(BOTypes.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObjectBuilder objectBuilder = Json.createObjectBuilder();

        ServletContext servletContext = getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = dbcp.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM customer");
            ResultSet resultSet = preparedStatement.executeQuery();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            while (resultSet.next()) {
                arrayBuilder.add(
                        Json.createObjectBuilder()
                                .add("nic", resultSet.getString(1))
                                .add("name", resultSet.getString(2))
                                .add("tel", resultSet.getString(3))
                                .add("address", resultSet.getString(4))
                );
            }
            resp.getWriter().print(
                    objectBuilder
                            .add("state", "Ok")
                            .add("message", "Successfully loaded...!")
                            .add("data", "[" + arrayBuilder.build() + "]")
                            .build());

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

        try{
            if (customerBO.addCustomer(new CustomerDTO(req.getParameter("nic"),req.getParameter("name"),req.getParameter("tel"),req.getParameter("address")))) {
                resp.getWriter().print(
                        objectBuilder
                                .add("state", "Ok")
                                .add("message", "Successfully Added...!")
                                .add("data", "[]")
                                .build()
                );
            }else {
                resp.getWriter().print(
                        objectBuilder
                                .add("state", "Error")
                                .add("message", "Not Added...!")
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
        JsonObject jsonObject = Json.createReader(req.getReader()).readObject();

        try {
            if (customerBO.updateCustomer(new CustomerDTO(jsonObject.getString("nic"),jsonObject.getString("name"),jsonObject.getString("tel"),jsonObject.getString("address")))) {
                resp.getWriter().print(
                        objectBuilder
                                .add("state", "Ok")
                                .add("message", "Successfully Updated...!")
                                .add("data", "[]")
                                .build()
                );
            }else{
                resp.getWriter().print(
                        objectBuilder
                                .add("state", "Error")
                                .add("message", "Not Added...!")
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

        ServletContext servletContext = getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");

        try (Connection connection = dbcp.getConnection()){
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE nic=?");
            preparedStatement.setObject(1, req.getParameter("nic"));
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
