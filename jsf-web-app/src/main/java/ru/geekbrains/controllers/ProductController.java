package ru.geekbrains.controllers;

import ru.geekbrains.persist.Product;
import ru.geekbrains.persist.ProductCategory;
import ru.geekbrains.persist.ProductCategoryRepository;
import ru.geekbrains.persist.ProductRepository;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ComponentSystemEvent;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class ProductController implements Serializable {

    @Inject
    private ProductRepository productRepository;

    @Inject
    private ProductCategoryRepository productCategoryRepository;

    private Product product;

    private List<Product> products;

    private List<ProductCategory> productCategories;

    public void preloadData(ComponentSystemEvent componentSystemEvent) {
        this.products = productRepository.findAll();
        this.productCategories = productCategoryRepository.findAll();
    }

    public List<Product> getAllProducts() {
        return products;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String editProduct(Product product) {
        this.product = product;
        return "/product.xhtml?faces-redirect=true";
    }

    public void deleteProduct(Product product) {
        productRepository.delete(product.getId());
    }

    public String saveProduct() {
        if (product.getId() == null) {
            productRepository.insert(product);
        } else {
            productRepository.update(product);
        }
        return "/index.xhtml?faces-redirect=true";
    }

    public String createProduct() {
        this.product = new Product();
        return "/product.xhtml?faces-redirect=true";
    }

    public List<ProductCategory> getProductCategories() {
        return productCategories;
    }
}
