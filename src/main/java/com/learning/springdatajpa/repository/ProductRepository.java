package com.learning.springdatajpa.repository;

import com.learning.springdatajpa.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductRepository extends RevisionRepository<Product,Integer,Integer>,
        JpaRepository<Product,Integer> {

    Product findByName(String name);

    List<Product> findByProductType(String productType);

    List<Product> findByPriceAndProductType(double price, String productType);


    //@Query(value = "SELECT * FROM product where price = ?1",nativeQuery = true)
    @Query("from Product p where p.price= ?1")
    //@Query("FROM Product p WHERE p.price= :price")
    List<Product> getProductByPrice(double price);

    List<Product> findByPriceIn(List<Double> prices);

    List<Product> findByPriceBetween(double minPrice, double maxPrice);

    List<Product> findByPriceGreaterThan(double price);

    List<Product> findByPriceLessThan(double price);

    List<Product> findByNameIgnoreCaseContaining(String name);
}
