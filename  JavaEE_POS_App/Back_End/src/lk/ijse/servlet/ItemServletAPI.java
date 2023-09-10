package lk.ijse.servlet;


import lk.ijse.bo.BOTypes;
import lk.ijse.bo.FactoryBO;
import lk.ijse.bo.castom.impl.ItemBOImpl;
import lk.ijse.dto.ItemDTO;
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

@WebServlet(urlPatterns = "/pages/item")
public class ItemServletAPI extends HttpServlet {
    private final ItemBOImpl itemBO = (ItemBOImpl) FactoryBO.getFactoryBO().getInstance(BOTypes.ITEM);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ArrayList<ItemDTO> allItems = itemBO.getAllItems();
            JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();
            for (ItemDTO i:allItems) {
                arrayBuilder.add(
                        Json.createObjectBuilder()
                        .add("code", i.getCode())
                        .add("name", i.getName())
                        .add("price", i.getPrice())
                        .add("qty",i.getQty())
                        .build()
                );
            }
            resp.getWriter().print(ResponseUtil.getResJsonObject("Ok","Successfully loaded...!",arrayBuilder.build()));
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(ResponseUtil.getResJsonObject("Error","Not loaded...!"));
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (itemBO.addItem(new ItemDTO(req.getParameter("code"), req.getParameter("name"), Double.parseDouble(req.getParameter("price")), Integer.parseInt(req.getParameter("qty"))))) {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Error","Successfully Added...!"));
            }else {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Error","Not Added...!"));
            }
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(ResponseUtil.getResJsonObject("Error",e.getMessage()));
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JsonObject jsonObject = Json.createReader(req.getReader()).readObject();
        try  {
            if (itemBO.updateItem(new ItemDTO(jsonObject.getString("code"),jsonObject.getString("name"),Double.parseDouble(jsonObject.getString("price")),Integer.parseInt(jsonObject.getString("qty"))))) {
                resp.getWriter().print(ResponseUtil.getResJsonObject("OK","Successfully Updated...!"));
            }else {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Error","Not Updated...!"));
            }
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(ResponseUtil.getResJsonObject("Error",e.getMessage()));
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try  {
            if (itemBO.deleteItem(new ItemDTO(req.getParameter("code")))) {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Ok","Successfully Deleted...!"));
            }else {
                resp.getWriter().print(ResponseUtil.getResJsonObject("Error","Not Deleted...!"));
            }
        } catch (SQLException e) {
            resp.setStatus(400);
            resp.getWriter().print(ResponseUtil.getResJsonObject("Error",e.getMessage()));
        }
    }
}
