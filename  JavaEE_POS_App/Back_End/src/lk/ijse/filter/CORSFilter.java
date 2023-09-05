package lk.ijse.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "/pages/*")
public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request=(HttpServletRequest) servletRequest;
        HttpServletResponse response=(HttpServletResponse) servletResponse;
        String method = request.getMethod();

        filterChain.doFilter(servletRequest,servletResponse);



        System.out.println(method);

        if (method.equals("OPTIONS")) {
            response.setStatus(200);
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "PUT,DELETE");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        }else {
            System.out.println("ok");
            response.addHeader("Content-Type", "application/json");
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
    }

    @Override
    public void destroy() {

    }
}
