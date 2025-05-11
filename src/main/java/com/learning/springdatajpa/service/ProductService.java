package com.learning.springdatajpa.service;

import com.learning.springdatajpa.entity.Product;
import com.learning.springdatajpa.repository.ProductRepository;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
@Slf4j
@CacheConfig(cacheNames = "products")
public class ProductService {

    @Autowired
    private ProductRepository repository;

    //@PostConstruct
    public void initDB(){
        List<Product> productList = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new Product(
                        "product" + i,
                        "desc" + i,
                        "productType" + i)).collect(Collectors.toList());
        repository.saveAll(productList);
    }


    @CachePut(key="#product.id")
    public Product saveProduct(Product product){
        return repository.save(product);
    }

    @Cacheable
    public List<Product> findAll(){
        log.info("ProductService ::::::find All");
        return repository.findAll();
    }

    @Cacheable(key="#id")
    public Product findById(int id){
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("Id doesnt exists"));
    }

    public Product findByName(String name){
        return repository.findByName(name);
    }

    public List<Product> findByProductType(String productType){
        return repository.findByProductType(productType);
    }

    public List<Product> findByPriceAndProductType(double price, String productType){
        return repository.findByPriceAndProductType(price,productType);
    }

    @CachePut(key="#id")
    public Product updateProduct(int id,Product product){
        Product oldProduct = findById(id);
        System.out.println(oldProduct.getProductType());
        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setProductType(product.getProductType());
        return repository.save(oldProduct);
    }

    @CacheEvict(key="#id")
    public long deleteProduct(int id){
        repository.deleteById(id);
        return repository.count();
    }

    public List<Product> getProductByMultiplePriceValue(List<Double> prices){
        return repository.findByPriceIn(prices);
    }

    public List<Product> getProductWithInPriceRange(double minPrice, double maxPrice){
        return repository.findByPriceBetween(minPrice,maxPrice);
    }

    public List<Product> getProductWithPriceGreaterThan(double price){
        return repository.findByPriceGreaterThan(price);
    }

    public List<Product> getProductWithPriceLessThan(double price){
        return repository.findByPriceLessThan(price);
    }

    public List<Product> getProductsWithLike(String name){
        return  repository.findByNameIgnoreCaseContaining(name);
    }

    //sorting
    public List<Product> getProductsWithSorting(String fieldName){
        return repository.findAll(Sort.by(Sort.Direction.ASC,fieldName));
    }

    //pagination
    public Page<Product> getProductWithPagination(int offset, int limit){
        return repository.findAll(PageRequest.of(offset,limit));
    }

    //sorting and pagination
    public Page<Product> getProductsWithSortingPagination(String fieldName,int offset,int limit){
        return repository.findAll(PageRequest.of(offset,limit).withSort(Sort.by(fieldName)));
    }

}
