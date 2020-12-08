package ru.geekbrains.persist;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Named
@ApplicationScoped
public class ProductCategoryRepository {

    @PersistenceContext(unitName = "ds")
    private EntityManager em;

    public ProductCategory findById(long id) {
        return em.find(ProductCategory.class, id);
    }

    public List<ProductCategory> findAll() {
        return em.createQuery("from ProductCategory t", ProductCategory.class)
                .getResultList();
    }
}
