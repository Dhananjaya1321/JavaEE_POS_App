package lk.ijse.servlet;

import lk.ijse.bo.BOTypes;
import lk.ijse.bo.FactoryBO;
import lk.ijse.bo.SuperBO;
import lk.ijse.bo.castom.impl.CustomerBOImpl;
import lk.ijse.dto.CustomerDTO;
import lk.ijse.listener.Listener;
import lk.ijse.util.ResponseUtil;
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

@WebServlet(urlPatterns = "/pages/customer")
public class CustomerServletAPI extends HttpServlet {
    private final CustomerBOImpl customerBO = (CustomerBOImpl) FactoryBO.getFactoryBO().getInstance(BOTypes.CUSTOMER);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = Listener.getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection= dbcp.getConnection()){
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            ArrayList<CustomerDTO> allCustomers = customerBO.getAllCustomers(connection);
            for (CustomerDTO c : allCustomers) {
                arrayBuilder.add(Json.createObjectBuilder().add("nic", c.getNic()).add("name", c.getName()).add("tel", c.getTel()).add("address", c.getAddress()));
            }
            resp.getWriter().print(ResponseUtil.getResJsonObject("Error", "Successfully loaded...!", arrayBuilder.build()));
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(ResponseUtil.getResJsonObject("Error", e.getMessage()));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = Listener.getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection= dbcp.getConnection()){
            if (customerBO.addCustomer(new CustomerDTO(req.getParameter("nic"), req.getParameter("name"), req.getParameter("tel"), req.getParameter("address")),connection)) {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Ok", "Successfully Added...!"));
            } else {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Error", "Not Added...!"));
            }
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(ResponseUtil.getResJsonObject("Error", e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonObject = Json.createReader(req.getReader()).readObject();

        ServletContext servletContext = Listener.getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection= dbcp.getConnection()){
            if (customerBO.updateCustomer(new CustomerDTO(jsonObject.getString("nic"), jsonObject.getString("name"), jsonObject.getString("tel"), jsonObject.getString("address")),connection)) {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Ok", "Successfully Updated...!"));
            } else {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Error", "Not Updated...!"));
            }
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(ResponseUtil.getResJsonObject("Error", e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = Listener.getServletContext();
        BasicDataSource dbcp = (BasicDataSource) servletContext.getAttribute("dbcp");
        try (Connection connection= dbcp.getConnection()){
            if (customerBO.deleteCustomer(new CustomerDTO(req.getParameter("nic")),connection)) {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Ok", "Successfully Deleted...!"));
            } else {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Error", "Not Deleted...!"));
            }
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(ResponseUtil.getResJsonObject("Error", e.getMessage()));
        }
    }
}
