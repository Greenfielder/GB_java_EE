package ru.geekbrains.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductRepository;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@WebListener
public class AppBootstrapListener implements ServletContextListener {

    private static final Logger logger = LoggerFactory.getLogger(AppBootstrapListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Initializing application");

        ServletContext sc = sce.getServletContext();
        String jdbcConnectionString = sc.getInitParameter("jdbcConnectionString");
        String username = sc.getInitParameter("username");
        String password = sc.getInitParameter("password");

        try {
            Connection connection = DriverManager.getConnection(jdbcConnectionString, username, password);
            sc.setAttribute("jdbcConnection", connection);

            ProductRepository productRepository = new ProductRepository(connection);
            sc.setAttribute("productRepository", productRepository);

            if (productRepository.findAll().size() == 0) {
                productRepository.insert(new Product(-1L, "TMNT comix", "TMNT super comix", 640));
                productRepository.insert(new Product(-1L, "Batman comix", "Batman comix #1", 500));
                productRepository.insert(new Product(-1L, "Spiderman comix", "Best comix ever", 700));
                productRepository.insert(new Product(-1L, "Mortal kombat comix", "Bloody comix", 490));
                productRepository.insert(new Product(-1L, "Star Wars comix", "Most epic comix", 670));
                productRepository.insert(new Product(-1L, "Garfield comix", "Garfield the cat for children", 300));
                productRepository.insert(new Product(-1L, "Mickey detective comix", "Mickey mouse for children", 300));
                productRepository.insert(new Product(-1L, "Iron Man comix", "Technologic comix", 300));
                productRepository.insert(new Product(-1L, "Boys comix", "Original movie comix", 300));
            }
        } catch (SQLException ex) {
            logger.error("Can't initialize JDBC connection", ex);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Closing JDBC connection");

        ServletContext sc = sce.getServletContext();
        Connection connection = (Connection) sc.getAttribute("jdbcConnection");

        try {
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException ex) {
            logger.error("Can't close JDBC connection", ex);
        }
    }

}
