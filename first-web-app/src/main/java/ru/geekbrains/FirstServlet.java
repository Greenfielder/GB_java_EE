package ru.geekbrains;

import javax.servlet.*;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

public class FirstServlet implements Servlet {
    private transient ServletConfig config;
    private final AtomicInteger requestCounter = new AtomicInteger();

    @Override
    public void init(ServletConfig config) throws ServletException {
        this.config = config;
    }

    @Override
    public ServletConfig getServletConfig() {
        return config;
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        getServletConfig().getServletContext().getRequestDispatcher("/page_header").include(req, res);
        res.getWriter().println("<h2>Содержание основной страницы</h2>");
    }

    @Override
    public String getServletInfo() {
        return "FirstServlet";
    }

    @Override
    public void destroy() {
    }
}