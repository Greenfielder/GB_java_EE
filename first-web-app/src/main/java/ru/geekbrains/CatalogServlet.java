package ru.geekbrains;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(urlPatterns = {"", "/", "/catalog","/product"})
public class CatalogServlet extends HttpServlet {

    private ProductRepository productRepository;

    @Override
    public void init() throws ServletException {
        productRepository = (ProductRepository) getServletContext().getAttribute("productRepository");
        if (productRepository == null) {
            throw new ServletException("Product repository not initialized");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            if (req.getServletPath().equals("/")) {
                List<Product> products = productRepository.findAll();
                req.setAttribute("products", products);
                getServletContext().getRequestDispatcher("/WEB-INF/views/index.jsp").forward(req, resp);

            } else if (req.getServletPath().equals("/product")) {
//                Product product = productRepository.findById(Long.parseLong(req.getParameter("param1")));
//                req.setAttribute("product", product);
                getServletContext().getRequestDispatcher("/WEB-INF/views/product.jsp").forward(req, resp);
            } else if (req.getServletPath().equals("/catalog")) {
                List<Product> products = productRepository.findAll();
                req.setAttribute("products", products);
                getServletContext().getRequestDispatcher("/WEB-INF/views/catalog.jsp").forward(req, resp);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    }
}