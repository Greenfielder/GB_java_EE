package ru.geekbrains.persist;


import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Named
@ApplicationScoped
public class ProductRepository {

    @Inject
    private ServletContext context;

    private Connection connect;

    @PostConstruct
    public void init() throws SQLException {
        this.connect = (Connection) context.getAttribute("jdbcConnection");
        createTableIfNotExists(connect);
    }

    private void createTableIfNotExists(Connection connect) throws SQLException {
        try (Statement stmt = connect.createStatement()) {
            stmt.execute("create table if not exists products (\n" +
                    "\tid int auto_increment primary key,\n" +
                    "    name varchar(30) NOT NULL,\n" +
                    "    description varchar(50),\n" +
                    "    price int(7) NOT NULL\n" +
                    ");");
        }
    }

    public List<Product> findAll() throws SQLException {
        List<Product> result = new ArrayList<>();
        try (Statement stmt = connect.createStatement()) {
            ResultSet rs = stmt.executeQuery("select * from products");
            while (rs.next()) {
                result.add(new Product(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4)));
            }
        }
        return result;
    }

    public Product findById(long id) throws SQLException {
        try (PreparedStatement stmt = connect.prepareStatement(
                "select id, name, description, price from products where id = ?")) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(rs.getLong(1), rs.getString(2), rs.getString(3), rs.getInt(4));
            }
        }
        return new Product(-1L, "", "", 0);
    }

    public void insert(Product product) throws SQLException {
        try (PreparedStatement stmt = connect.prepareStatement(
                "insert into products(name, description, price) values (?, ?, ?);")) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getPrice());
            stmt.execute();
        }
    }

    public void update(Product product) throws SQLException {
        try (PreparedStatement stmt = connect.prepareStatement(
                "update products set name = ?, description = ?, price = ? where id = ?;")) {
            stmt.setString(1, product.getName());
            stmt.setString(2, product.getDescription());
            stmt.setInt(3, product.getPrice());
            stmt.setLong(4, product.getId());
            stmt.execute();
        }
    }

    public void delete(long id) throws SQLException {
        try (PreparedStatement stmt = connect.prepareStatement(
                "delete from products where id = ?;")) {
            stmt.setLong(1, id);
            stmt.execute();
        }
    }

}
