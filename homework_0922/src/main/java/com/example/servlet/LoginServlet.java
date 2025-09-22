package com.example.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.annotation.WebInitParam;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginServlet extends HttpServlet {
    private String systemName;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        systemName = config.getInitParameter("systemName");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");

        ServletContext context = getServletContext();
        String version = context.getInitParameter("version");

        // 简单校验，请设置自己的账户名和密码
        if ("lilixin".equals(username) && "911005".equals(password)) {
            request.setAttribute("msg", "欢迎使用 " + systemName + " (版本: " + version + ")");
            request.getRequestDispatcher("welcome.jsp").forward(request, response);
        } else {
            response.sendRedirect("error.jsp");
        }
    }
}